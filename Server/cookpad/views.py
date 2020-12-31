from django.shortcuts import render
from django.http import HttpResponse
from .models import *

# Create your views here.

loginAccountIP = []

def getLoginToken(request):
    try:
        if request.method == 'GET':
            name = request.GET['n']
            passw = request.GET['p']
            valid = request.GET['c']
            ac = Account.objects.all()
            for acci in ac:
                truename = acci.getUserName()
                truepass = acci.getPassword()

                if name == truename and passw == truepass:
                    loginAccountIP.append(getClientIP(request));
                    return HttpResponse("Login success")
                    #return HttpResponse(str(5*int(valid)+1))
                    break
                else:
                    continue
            return HttpResponse('Login fail')
            
        else:
            return HttpResponse('404: Page not found')

    except:
        return HttpResponse("404: Page not found")
    
    pass
def getLogoutToken(request):
    try:
        if request.method == 'GET':
            name = request.GET['n']
            valid = request.GET['c']
            clientIp = getClientIP(request)
            #if(len(loginAccountIP)>0):
            #    return HttpResponse(loginAccountIP[0])
            #else:
            #    return HttpResponse("empty list")
            
            for it in range(len(loginAccountIP)): 
                #return HttpResponse(loginAccountIP[it])
                try:
                    if clientIp == loginAccountIP[it]:

                        #return HttpResponse(clientIp)
                        del loginAccountIP[it]
                        return HttpResponse("Logged out")
                        #return HttpResponse(str(5*int(valid)+1))
                        break
                    else:
                        continue
                except:
                    return HttpResponse("yeah that it")
            return HttpResponse('Account hasn\'t loggin')
            
        else:
            return HttpResponse('404: Page not found')

    except:
        return HttpResponse("404: Page not found")
    
    pass

def pageNotFound(request):
    return HttpResponse("404: Page not found")

def getClientIP(request):
    #ip = request.META.get('REMOTE_ADDR')

    x_forwarded_for = request.META.get('HTTP_X_FORWARDED_FOR')
    if x_forwarded_for:
        ip = x_forwarded_for.split(',')[0]
    else:
        ip = request.META.get('REMOTE_ADDR')
    return ip