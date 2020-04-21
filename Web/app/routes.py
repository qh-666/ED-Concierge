import nexmo

from random import randint

from flask import render_template, request, redirect, url_for
from firebase_admin import messaging, firestore

from app import app, db
from app.models import User, Post

current_user = None
current_hospital = None

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        return redirect('/hospital/{}'.format(request.form['hospital']))
    else:
        return render_template('index.html')


@app.route('/hospital/<hospital>')
def main(hospital):
    users = User.query.filter_by(hospitalname=hospital).all()
    return render_template('main.html', users=users, hospital=hospital, selected_user=None)

@app.route('/hospital/<hospital>/patient/<patient_id>')
def message(hospital, patient_id):
    users = User.query.filter_by(hospitalname=hospital).all()
    selected_user = User.query.filter_by(id=patient_id).first()
    return render_template('main.html', users=users, hospital=hospital, selected_user=selected_user)



@app.route('/hospital/<hospital>/create_user', methods=['POST'])
def create_user(hospital):
    username = request.form['username']
    id_number = request.form['id_number']
    hospitalname = hospital
    phonenum = request.form['phonenum']
    vericode = randint(100000, 999999)
    user = User(id=id_number, username=username, hospitalname=hospitalname)
    db.session.add(user)
    db.session.commit()

    client = nexmo.Client(key='******', secret='******')
    client.send_message({
        'from': '******',
        'to': str(phonenum),
        'text': 'Hi, ' + username + '. Welcome to ' + hospitalname +'.\nYour patient id: ' + id_number + '.\nYour login code for ED Concierge: ' + str(vericode) + '.'
    })

    print('phonenum', phonenum)
    print('code', vericode)

    firestore.client().collection(u'users').add({
        u'hospital': hospitalname,
        u'id': id_number,
        u'name': username,
        u'vericode': str(vericode)
    })
    return redirect(request.referrer)


@app.route('/hospital/<hospital>/patient/<patient_id>/create_notification', methods=['POST'])
def create_notification(hospital, patient_id):
    notification_content = request.form['notification_content']
    user = User.query.filter_by(id=patient_id).first()
    p = Post(notification_content=notification_content, author=user)
    db.session.add(p)
    db.session.commit()
    topic = patient_id
    message = messaging.Message(
        notification = messaging.Notification(
            title = 'ED Concierge',
            body = notification_content,
        ),
        data={
            'hospitalMessage': notification_content
        },
        topic=topic,
    )
    response = messaging.send(message)
    return redirect(request.referrer)
