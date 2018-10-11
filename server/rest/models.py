from django.db import models
from django.utils import timezone

# Create your models here.

class User_info(models.Model):
    first_name = models.CharField(max_length=20)
    last_name = models.CharField(max_length=20)
    sex = models.IntegerField(default = 0)      # 0 : male, 1 : female
    age = models.IntegerField()
    email = models.EmailField()
    join_date = models.DateTimeField(blank=True, null=True)

    def join(self):
        self.join_date = timezone.now()
        self.save()

    def __str__(self):
        return ("")

class User(models.Model):
    auth_id = models.CharField(max_length=20)
    auth_pw = models.CharField(max_length=20)