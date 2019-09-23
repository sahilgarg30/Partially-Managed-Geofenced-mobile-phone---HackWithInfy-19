from django.shortcuts import render
from django.views.generic import ListView
from hackathon_project.pyrebase_settings import db, auth, user
from django.shortcuts import redirect
from django.urls import reverse

# Create your views here.

class BatteryStatus(ListView):
    template_name           = 'battery/battery.html'

    def get_queryset(self):
        return db.child('battery').get(user['idToken']).val()