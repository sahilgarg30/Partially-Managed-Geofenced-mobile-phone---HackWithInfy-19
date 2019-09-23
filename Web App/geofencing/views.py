from django.shortcuts import render
from django.views.generic import ListView
from hackathon_project.pyrebase_settings import user, db, auth

# Create your views here.

class Alert(ListView):
    template_name           = 'geofencing/geofencing.html'

    def get_queryset(self):
        return db.child('Location').get(user['idToken']).val()