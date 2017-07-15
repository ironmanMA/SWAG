var recommendedItems=[]

jQuery(".recommended-items .repeat-wrapper").each(function( index ) {


	var foodImgSrc = $(this).find("img").attr("src").trim();
	var foodTitle = $(this).find("h5.recommended.text-ellipsis").text().trim();
	var foodPrice = $(this).find(".item-price").text();
	// console.log( index+","+
	// foodTitle +","+
	// foodPrice +","+
	// foodImgSrc );
	var foodItem={
		"restaurantName":jQuery(".menu-title span[itemprop='name']").text().trim(),
		"restaurantRating":jQuery(".rating span[itemprop='ratingValue']").text().trim(),
		"restaurantReviewCount":jQuery(".rating meta[itemprop='reviewCount']").attr('content').trim(),
		"restaurantDeliveryTime":"41",
		"foodImgSrc":foodImgSrc,
		"foodTitle":foodTitle,
		"foodPrice":foodPrice,
		"foodDesc":""
	}
	recommendedItems.push(foodItem)
});

// JSON.stringify(recommendedItems);

for(var category in App.menu.categories){
	if(App.menu.categories[category]["subCategories"] != null){
		for(var subCategory in App.menu.categories[category]["subCategories"]){
			for(var menuItem in App.menu.categories[category]["subCategories"][subCategory]["menu"]){
				// console.log(App.menu.categories[category]["menu"][menuItem])
				for(var dish in recommendedItems){
					if (recommendedItems[dish]["foodTitle"]===App.menu.categories[category]["menu"][menuItem]["name"])
						recommendedItems[dish]["foodDesc"]=App.menu.categories[category]["menu"][menuItem]["description"]
				}
			}
		}
	}else{
		for(var menuItem in App.menu.categories[category]["menu"]){
			// console.log(App.menu.categories[category]["menu"][menuItem])
			for(var dish in recommendedItems){
				if (recommendedItems[dish]["foodTitle"]===App.menu.categories[category]["menu"][menuItem]["name"])
					recommendedItems[dish]["foodDesc"]=App.menu.categories[category]["menu"][menuItem]["description"]
			}
		}
	}

}

JSON.stringify(recommendedItems);
