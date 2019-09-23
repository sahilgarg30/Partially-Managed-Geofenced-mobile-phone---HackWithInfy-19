from django.shortcuts import render
from django.views.generic import ListView
from hackathon_project.pyrebase_settings import db, auth, user
from django.shortcuts import redirect
from django.urls import reverse

# Create your views here.

class Block(ListView):
    template_name           = 'block/block.html'

    def get_queryset(self):
        boolVal             = db.child('blockApp').get(user['idToken']).val()

        return boolVal

class Toggle(ListView):
    template_name           = 'block/toggle.html'

    def get(self, request):
        boolVal             = db.child('blockApp').get(user['idToken']).val()

        if boolVal == 0:
            db.child('blockApp').set(1)
        else:
            db.child('blockApp').set(0)

        return redirect(reverse('block:block'))