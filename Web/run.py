import firebase_admin
from firebase_admin import credentials
from app import app, db

db.create_all()
# cred = credentials.Certificate("/Users/liutianyi/Downloads/ed-concierge-firebase-adminsdk-koj7c-54b51bb99e.json")
cred = credentials.Certificate("/home/ubuntu/Documents/ed-concierge-firebase.json")
firebase_admin.initialize_app(cred)

app.run(host='0.0.0.0', port='80')
# app.run(host='0.0.0.0', port='5000')
