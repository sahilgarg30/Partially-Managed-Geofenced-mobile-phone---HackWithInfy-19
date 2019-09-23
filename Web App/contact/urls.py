from django.urls import path
from . import views

app_name            = 'contact'

urlpatterns         = [
    path('', views.ContactDetails.as_view(), name = 'contact_details'),
    path('delete/<str:id>', views.delete, name = 'delete')
]