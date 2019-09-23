from rest_framework import serializers
from . import models

class QRCodeSerializer(serializers.ModelSerializer):
    class Meta:
        model           = models.QRCode
        fields          = [
            'content'
        ]

class GeofencingSerializer(serializers.ModelSerializer):
    class Meta:
        model           = models.Geofencing
        fields          = [
            'content'
        ]