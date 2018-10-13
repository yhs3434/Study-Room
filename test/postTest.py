import requests
URL = 'http://127.0.0.1:8000/rest/find/group/'

data = {'id':1}
res = requests.post(URL, json=data)
print(res)