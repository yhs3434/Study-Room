from django.urls import path, include
from . import views

urlpatterns = [
    path('join/', views.user_list),
    path('join/<int:pk>/', views.user_detail),
    path('sign_in/', views.user_login),
    path('choice/subject/', views.choice_subject),
    path('choice/tendency/', views.choice_tendency),
    path('find/group/', views.FindGroup.as_view()),
    path('find/group/<int:pk>/', views.FindGroupDetail.as_view()),
]