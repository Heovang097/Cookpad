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
class Message(models.Model):
    idUserSender = models.CharField(max_length = 8)
    idUserReceiver = models.CharField(max_length = 8)
    content = models.CharField(max_length = 255, null=False)
class LikeUser(models.Model):
    idUser = models.CharField(max_length = 8)
    idUserTarget = models.CharField(max_length = 8)
    date = models.DateField()
class Follow(models.Model):
    idUser = models.CharField(max_length = 8)
    idUserTarget = models.CharField(max_length = 8)
    date = models.DateField()

class Like(models.Model):
    idUser = models.CharField(max_length = 8)
    idUserTarget = models.CharField(max_length = 8)
    date = models.DateField()

class Block(models.Model):
    idAdmin = models.CharField(max_length = 8)
    idUser = models.CharField(max_length = 8)
    date = models.DateField()
    content = models.CharField(max_length = 255)

class LikeCmt(models.Model):
    idUser = models.CharField(max_length = 8)
    idCommentTarget = models.CharField(max_length = 8)

class Recipes(models.Model):
    idRecipes = models.CharField(max_length = 8, primary_key=True)
    content = models.CharField(max_length = 5000, null=False)

class CommentRecipe(models.Model):
    idComment = models.CharField(max_length = 8, primary_key=True)
    idUser = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    idRecipes = models.ForeignKey(Recipes, related_name="+", on_delete=models.CASCADE)
    idCommentTarget = models.ForeignKey('self', related_name="+", on_delete=models.CASCADE)
    content = models.CharField(max_length = 255)

class RecipesUser(models.Model):
    idRecipes = models.CharField(max_length = 8)
    idUser = models.CharField(max_length = 8)
    date = models.DateField()

class LikeRecipes(models.Model):
    idUser = models.CharField(max_length = 8)
    idRecipes = models.CharField(max_length = 8)
    type = models.IntegerField()
class Share(models.Model):
    idUser = models.ForeignKey(Recipes, related_name="+", on_delete=models.CASCADE)
    idRecipes = models.ForeignKey(User, related_name="+", on_delete=models.CASCADE)
    content = models.CharField(max_length = 255)
    date = models.DateField()



