from django.urls import path
from . import views

urlpatterns         = [
    path('update/qr_code/', views.QRCodeCreateAPIView.as_view()),
    path('update/geofencing/', views.GeofencingCreateAPIView.as_view())
]