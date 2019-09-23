from django.urls import path
from . import views

app_name            = 'qr_code_display'

urlpatterns         = [
    path('', views.QRList.as_view(), name = 'qr_list')
]