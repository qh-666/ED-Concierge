``` py
import firebase_admin
from firebase_admin import credentials, messaging

cred = credentials.Certificate("/Users/liutianyi/Downloads/ed-concierge-firebase-adminsdk-koj7c-54b51bb99e.json")
firebase_admin.initialize_app(cred)

topic = '00000001'
message = messaging.Message(
    notification = messaging.Notification(
        title = 'Hi Benwei, come back it\'s your turn',
        body = 'Hi Benwei, come back it\'s your turn.',
    ),
    data={
        'hospitalMessage': 'Hi Benwei, come back it\'s your turn.'
    },
    topic=topic,
)
response = messaging.send(message)

print('Successfully sent message:', response)

```