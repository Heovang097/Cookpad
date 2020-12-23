from django.db import models

# Create your models here.
class Account(models.Model):
    name = models.CharField(max_length=50)
    passw = models.CharField(max_length=50)

    def getAccName(self):
        return self.name
        pass
    def getAccPassw(self):
        return self.passw
        pass

