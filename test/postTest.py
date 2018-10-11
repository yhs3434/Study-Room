import requests
URL = 'http://127.0.0.1:8000/rest/choice/subject/'

data = {'id':2, '어학':False, '프로그래밍':False, '고시':True}
res = requests.post(URL, json=data)
print(res)