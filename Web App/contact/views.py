from django.shortcuts import render
from django.views.generic import ListView, DeleteView
from hackathon_project.pyrebase_settings import user, db, auth
from django.shortcuts import redirect
from django.urls import reverse, reverse_lazy
from django import forms
from django.contrib import messages

# Create your views here.

class ContactDetails(ListView):
    template_name           = 'contact/contact_details.html'

    def get_queryset(self):
        phoneNumbers    = db.child('phoneNumbers').get(user['idToken'])
        qs              = []

        for phone in phoneNumbers.each():
            dict        = phone.val()
            dict['id']  = phone.key()
            qs.append(dict)
        
        return qs
        
    def post(self, request):
        retVal       = {}

        retVal['name']          = request.POST['name']
        retVal['phone']         = request.POST['phone']

        if retVal['name'] == "":
            messages.error(request, "Name should not be null")
            return redirect(reverse('contact:contact_details'))

        if retVal['phone'] == "":
            messages.error(request, "Phone number should not be null")
            return redirect(reverse('contact:contact_details'))

        try:
            int(retVal['phone'])
            db.child('phoneNumbers').push(retVal)
        except:
            messages.error(request, "Phone number should be integer")

        return redirect(reverse('contact:contact_details'))

def delete(request, **kwargs):
    db.child("phoneNumbers").child(kwargs['id']).remove()

    return redirect(reverse('contact:contact_details'))