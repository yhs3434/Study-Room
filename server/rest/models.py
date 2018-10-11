from django.db import models
from django.utils import timezone

# Create your models here.

'''
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
'''

class User(models.Model):
    auth_id = models.CharField(max_length=20)
    auth_pw = models.CharField(max_length=20)

class Group(models.Model):
    name = models.CharField(max_length=20)
    description = models.CharField(max_length=20)

class User_Group(models.Model):
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    group_id = models.ForeignKey(Group, on_delete=models.CASCADE)
    joined = models.DateTimeField(timezone.now())
    role = models.CharField(max_length=20)

    class Meta:
        unique_together = (("user_id", "group_id"),)

class Subject(models.Model):
    name = models.CharField(max_length=20)

class User_Subject(models.Model):
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    subject_id = models.ForeignKey(Subject, on_delete=models.CASCADE)

    class Meta:
        unique_together = (("user_id", "subject_id"),)

class Tendency(models.Model):
    name = models.CharField(max_length=20)

class User_Tendency(models.Model):
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    tendency_id = models.ForeignKey(Tendency, on_delete=models.CASCADE)
    choice = models.IntegerField()

    class Meta:
        unique_together = (("user_id", "tendency_id"),)
