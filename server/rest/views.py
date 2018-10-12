from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from rest_framework.decorators import api_view
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from .models import User, Group, Subject, Tendency
from .models import User_Group, User_Subject, User_Tendency
from .serializers import UserSerializer, UserSubjectSerializer, UserTendencySerializer
from rest_framework import status
from rest_framework.response import Response

# Create your views here.

def index(request):
    return render(request, 'rest/index.html', {})

@api_view(['GET', 'POST'])
def user_list(request):
    if request.method == 'GET':
        users = User.objects.all()
        serializer = UserSerializer(users, many=True)
        return JsonResponse(serializer.data, safe=False)

    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = UserSerializer(data = data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['GET', 'PUT', 'DELETE'])
def user_detail(request, pk):
    try:
        user = User.objects.get(pk=pk)
    except(User.DoesNotExist):
        return (HttpResponse(status=404))

    if (request.method == 'GET'):
        serializer = UserSerializer(user)
        return (JsonResponse(serializer.data))
    
    elif (request.method == 'PUT'):
        data = JSONParser().parse(request)
        serializer = UserSerializer(user, data=data)
        if serializer.is_valid():
            serializer.save()
            return (JsonResponse(serializer.data))
        return (JsonResponse(serializer.errors, status=404))

    elif request.method == 'DELETE':
        user.delete()
        return (HttpResponse(status=204))

@api_view(['POST'])
def user_login(request):
    if (request.method == 'POST'):
        data = JSONParser().parse(request)
        try:
            user = User.objects.filter(auth_id = data['auth_id'])
            if (user[0].auth_pw == data['auth_pw']):
                serializer = UserSerializer(user[0])
                return (Response(data=serializer.data, status=status.HTTP_200_OK))
            else:
                return (Response(status=status.HTTP_404_NOT_FOUND))
        except(User.DoesNotExist):
            return (Response(status=status.HTTP_404_NOT_FOUND))

    else:
        return (Response(status = status.HTTP_400_BAD_REQUEST))

@api_view(['GET', 'POST'])
def choice_subject(request):
    if (request.method == 'POST'):
        data = JSONParser().parse(request)
        user_id = data['id']
        list = []

        for key in data:
            if(key == 'id'):
                continue
            try:
                subject_id = (Subject.objects.get(name=key)).id
                if(data[key] == 1):
                    insert = User_Subject.objects.create(user_id=User.objects.get(pk=user_id), subject_id=Subject.objects.get(pk=subject_id))
                    list.append(insert)
                else:
                    queryset = User_Subject.objects.all()
                    queryset = queryset.filter(user_id=User.objects.get(pk=user_id), subject_id=Subject.objects.get(pk=subject_id))
                    queryset.delete()
            except:
                continue
            
        return Response(status=status.HTTP_200_OK)
    
    elif (request.method == 'GET'):
        user_subject = User_Subject.objects.all()
        serializer = UserSubjectSerializer(user_subject, many=True)
        return Response(data=serializer.data, status=status.HTTP_200_OK)

    else:
        return Response(status=status.HTTP_404_NOT_FOUND)

@api_view(['POST', 'GET'])
def choice_tendency(request):
    if (request.method=='POST'):
        data = JSONParser().parse(request)
        user_id = data['id']
        user = User.objects.get(pk=user_id)

        try:
            queryset = User_Tendency.objects.filter(user_id=user)
            queryset.delete()
        except:
            print('user(',user_id,') choose tendencies.')

        try:
            insert = User_Tendency.objects.create(user_id=user, rule=data['규칙'], learning=data['학습량'], \
            numberPeople=data['인원'], friendship=data['친목'], environment=data['환경'], style=data['스타일'])
        except:
            return Response(status=status.HTTP_406_NOT_ACCEPTABLE)
        return Response(status=status.HTTP_200_OK)

    elif (request.method=='GET'):
        user_tendency = User_Tendency.objects.all()
        serializer = UserTendencySerializer(user_tendency, many=True)
        return Response(data=serializer.data, status=status.HTTP_200_OK)

    else:
        return Response(status=status.HTTP_404_NOT_FOUND)