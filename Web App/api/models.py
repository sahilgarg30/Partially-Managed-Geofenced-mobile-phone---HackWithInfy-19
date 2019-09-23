from django.shortcuts import render
from django.db import models

# Create your views here.

class QRCodeQuerySet(models.QuerySet):
    pass

class GeofencingQuerySet(models.QuerySet):
    pass

class QRCodeManager(models.Manager):
    def get_queryset(self):
        return QRCodeQuerySet(self.model, using = self._db)

class GeofencingManager(models.Manager):
    def get_queryset(self):
        return GeofencingQuerySet(self.model, using = self._db)

class QRCode(models.Model):
    content         = models.TextField()
    objects         = QRCodeManager()

class Geofencing(models.Model):
    content         = models.TextField()
    objects         = GeofencingManager()