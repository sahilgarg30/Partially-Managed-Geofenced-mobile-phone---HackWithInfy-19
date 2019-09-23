from django.shortcuts import render
from django.views.generic import ListView
from hackathon_project.pyrebase_settings import user, db, auth

# Create your views here.

class QRList(ListView):
    template_name           = 'qr_code_display/qr_list.html'

    def get_queryset(self):
        return db.child('qrCode').get(user['idToken']).val()