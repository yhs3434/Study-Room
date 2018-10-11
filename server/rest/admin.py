from django.contrib import admin
from .models import User, Group, Subject, Tendency
from .models import User_Group, User_Subject, User_Tendency

@admin.register(User)
class UserAdmin(admin.ModelAdmin):
    list_display = ['id', 'auth_id', 'auth_pw']
    list_display_links = ['id']


@admin.register(Group)
class GroupAdmin(admin.ModelAdmin):
    list_display = ['id', 'name', 'description']
    list_display_links = ['id']

@admin.register(User_Group)
class UserGroupAdmin(admin.ModelAdmin):
    list_display = ['id', 'user_id', 'group_id', 'joined', 'role']
    list_display_links = ['id']

@admin.register(Subject)
class SubjectAdmin(admin.ModelAdmin):
    list_display = ['id', 'name']
    list_display_links = ['id']

@admin.register(User_Subject)
class UserSubjectAdmin(admin.ModelAdmin):
    list_display = ['id', 'user_id', 'subject_id']
    list_display_links = ['id']

@admin.register(Tendency)
class TendencyAdmin(admin.ModelAdmin):
    list_display = ['id', 'name']
    list_display_links = ['id']

@admin.register(User_Tendency)
class UserTendencyAdmin(admin.ModelAdmin):
    list_display = ['id', 'user_id', 'tendency_id', 'choice']
    list_display_links = ['id']

'''
admin.site.register(User)
admin.site.register(Group)
admin.site.register(Subject)
admin.site.register(Tendency)
admin.site.register(User_Group)
admin.site.register(User_Subject)
admin.site.register(User_Tendency)
'''