from django.urls import path
from . import views

app_name            = 'battery'

urlpatterns         = [
    path('', views.BatteryStatus.as_view(), name = 'battery')
]