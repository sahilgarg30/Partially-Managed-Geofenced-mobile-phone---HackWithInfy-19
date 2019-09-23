from rest_framework import mixins, generics
from . import models
from . import serializers as srlz
from django.http import HttpResponse

class QRCodeCreateAPIView(generics.CreateAPIView):
    permission_classes      = []
    authentication_classes  = []
    serializer_class        = srlz.QRCodeSerializer
    queryset                = models.QRCode.objects.all()

    def create(self, serializer):
        print("ok")
        HttpResponse("<script>alert('hello');</script>")
        pass
    # def get(self, request, *args, **kwargs):
    #     HttpResponse("<script>location.reload();</script>")
    #     return self.get(request, *args, **kwargs)

class GeofencingCreateAPIView(generics.RetrieveAPIView):
    permission_classes      = []
    authentication_classes  = []
    serializer_class        = srlz.GeofencingSerializer
    queryset                = models.Geofencing.objects.all()

    def create(self, serializer):
        HttpResponse("<script>location.reload();</script>")
        pass
    # def get(self, request, *args, **kwargs):
    #     HttpResponse("<script>location.reload();</script>")
    #     return self.get(request, *args, **kwargs)