import json

finalString=""

with open('foodWithImages.json') as data_file:
    data = json.load(data_file)

iter=1
for obj in data["items"]:
	print "{\"index\":{\"_index\":\"swag\", \"_type\":\"menu\", \"_id\": "+str(iter)+" }}"+json.dumps(obj)
	iter+=1
