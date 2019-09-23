from django.urls import path
from . import views

app_name            = 'block'

urlpatterns         = [
    path('', views.Block.as_view(), name = 'block'),
    path('toggle/', views.Toggle.as_view(), name = 'toggle')
]