from django.contrib import admin
from .models import *
# Register your models here.
admin.site.register(User)
admin.site.register(Admin)
admin.site.register(Account)
admin.site.register(Message)
admin.site.register(LikeUser)
admin.site.register(Follow)
admin.site.register(Like)
admin.site.register(Block)
admin.site.register(LikeCmt)
admin.site.register(Recipes)
admin.site.register(CommentRecipe)
admin.site.register(RecipesUser)
admin.site.register(LikeRecipes)
admin.site.register(Share)

