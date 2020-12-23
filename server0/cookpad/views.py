from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.

def check_account(request):
    if request.method == 'POST':
        name = request.POST['name']
        passw = request.POST['pass']
        if name == 'admin' and passw == 'pw':

            return HttpResponse('Login success')
        else:
            return HttpResponse('Login fail')
    else:
        return HttpResponse('Login fail')
    pass