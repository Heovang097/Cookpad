from django.db import models

# Create your models here.
#class Account(models.Model):
#    name = models.CharField(max_length=50)
#    passw = models.CharField(max_length=50)
#
#    def getAccName(self):
#        return self.name
#        pass
#    def getAccPassw(self):
#        return self.passw
#        pass
#

class Faculty(models.Model):
    name = models.CharField(max_length=255)
    def getStudents(self):
        return self.students 
        pass
class Student(models.Model):
    fullname = models.CharField(max_length=50)
    faculty = models.ForeignKey(Faculty, related_name="students", on_delete=models.CASCADE)


