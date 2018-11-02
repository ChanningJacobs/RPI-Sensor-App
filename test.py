import requests
import base64
from firebase import firebase

db_url = 'https://safe-8cca1.firebaseio.com'
firebase = firebase.FirebaseApplication(db_url, None)

user_name = "Channing"
encoded_str = ''
with open('./image/download.jpeg') as file:
	encoded_str = base64.b64encode(file.read())

post_res = firebase.post('/users', {'user':user_name, 'image':encoded_str})
get_res = firebase.get('/users', post_res['name'])

print get_res

res = firebase.delete('/users', post_res['name'])

#result = firebase.patch('/users/-LQK2FFLuJxJ1Za9yJ9P',  {'image': encoded_string})
