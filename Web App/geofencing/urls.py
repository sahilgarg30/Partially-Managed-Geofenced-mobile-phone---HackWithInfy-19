from django.urls import path
from . import views

app_name        = 'geofencing'

urlpatterns     = [
    path('geofencing/', views.Alert.as_view(), name = 'geofencing')
]