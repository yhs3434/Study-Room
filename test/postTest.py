import requests
URL = 'http://127.0.0.1:8000/rest/choice/tendency/'

data = {'id':6, '규칙':0, '학습량':0, '인원':2, '친목':1, '환경':0, '스타일':2}
res = requests.post(URL, json=data)
print(res)