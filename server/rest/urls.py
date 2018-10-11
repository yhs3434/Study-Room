from django.urls import path, include
from . import views

urlpatterns = [
    path('join/', views.user_list),
    path('join/<int:pk>/', views.user_detail),
    path('sign_in/', views.user_login),
]