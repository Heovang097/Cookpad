from django.db import models

# Create your models here.
class User(models.Model):
    idUser = models.CharField(max_length = 8, primary_key=True)
    name = models.CharField(max_length = 255, null=False)
    birthDate = models.DateField(null=False)
    country = models.CharField(max_length = 255)

class Admin(models.Model):
    idAdmin = models.CharField(max_length = 8, primary_key=True)
    name = models.CharField(max_length = 255, null=False)
    birthDate = models.DateField(null=False)
    country = models.CharField(max_length = 255)

class Account(models.Model):
    idUser = models.CharField(max_length = 8, primary_key=True)
    userName = models.CharField(max_length = 255)
    password = models.CharField(max_length = 255)
    def getID(self):
        return self.idUser
        pass
    def getUserName(self):
        return self.userName
    def getPassword(self):
        return self.password

class Message(models.Model):
    idUserSender = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idUserReceiver = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    content = models.CharField(max_length = 200, null=False)
    datetime = models.DateTimeField()

class LikeUser(models.Model):
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idUserTarget = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    date = models.DateField()

class Follow(models.Model):
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idUserTarget = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    date = models.DateField()

class Like(models.Model):
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idUserTarget = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    date = models.DateField()

class Block(models.Model):
    idAdmin = models.CharField(max_length = 8)
    idUser = models.CharField(max_length = 8)
    date = models.DateField()
    content = models.CharField(max_length = 255)

class Recipes(models.Model):
    idRecipes = models.CharField(max_length = 8, primary_key=True)
    contentPath = models.CharField(max_length = 150, null=False)
    isPublic = models.BooleanField(default=False)

class CommentRecipe(models.Model):
    idComment = models.CharField(max_length = 8, primary_key=True)
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idRecipes = models.ForeignKey(Recipes, related_name="+", on_delete=models.CASCADE)
    idCommentTarget = models.ForeignKey('self', related_name="+", on_delete=models.CASCADE, null=True, blank=True)
    content = models.CharField(max_length = 255)

class LikeCmt(models.Model):
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idCommentTarget = models.ForeignKey(CommentRecipe, related_name="+", on_delete=models.CASCADE)


class RecipesUser(models.Model):
    idRecipes = models.CharField(max_length = 8)
    idUser = models.CharField(max_length = 8)
    date = models.DateField()

class LikeRecipes(models.Model):
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idRecipes = models.ForeignKey(Recipes, related_name="+", on_delete=models.CASCADE)
    type = models.IntegerField()

class Share(models.Model):
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idRecipes = models.ForeignKey(Recipes, related_name="+", on_delete=models.CASCADE)
    content = models.CharField(max_length = 255)
    date = models.DateField()



