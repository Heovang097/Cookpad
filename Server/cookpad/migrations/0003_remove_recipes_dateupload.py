# Generated by Django 3.1.4 on 2020-12-31 17:47

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('cookpad', '0002_auto_20201231_1729'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='recipes',
            name='dateUpload',
        ),
    ]
