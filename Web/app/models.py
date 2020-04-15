from datetime import datetime
from app import db

class User(db.Model):
    id = db.Column(db.String(64), primary_key=True)
    username = db.Column(db.String(64), index=True)
    hospitalname = db.Column(db.String(64))
    posts = db.relationship('Post', backref='author', lazy='dynamic')
    def __repr__(self):
        """
        return(str) objects of this class
        """
        return '<User {}>'.format(self.username)


class Post(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    notification_content = db.Column(db.String(16000))
    timestamp = db.Column(db.DateTime, index=True, default=datetime.utcnow)
    user_id = db.Column(db.String(64), db.ForeignKey('user.id'))
    def __repr__(self):
        return '<Post {}>'.format(self.body)