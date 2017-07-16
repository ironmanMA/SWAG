import json

finalString=""

with open('foodWithImages.json') as data_file:
    data = json.load(data_file)

for obj in data:
	print obj["foodTitle"]
