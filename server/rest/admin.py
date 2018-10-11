from django.contrib import admin
from .models import User, Group, Subject, Tendency
from .models import User_Group, User_Subject, User_Tendency

admin.site.register(User)
admin.site.register(Group)
admin.site.register(Subject)
admin.site.register(Tendency)
admin.site.register(User_Group)
admin.site.register(User_Subject)
admin.site.register(User_Tendency)