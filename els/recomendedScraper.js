var recommendedItems=[]

jQuery(".recommended-items .repeat-wrapper").each(function( index ) {


	var foodImgSrc = $(this).find("img").attr("src").trim();
	var foodTitle = $(this).find("h5.recommended.text-ellipsis").text().trim();
	var foodPrice = $(this).find(".item-price").text();
	console.log( index+","+
	foodTitle +","+
	foodPrice +","+
	foodImgSrc );
	var foodItem={
		"restaurantName":jQuery(".menu-title span[itemprop='name']").text().trim(),
		"restaurantRating":jQuery(".rating span[itemprop='ratingValue']").text().trim(),
		"restaurantReviewCount":jQuery(".rating meta[itemprop='reviewCount']").attr('content').trim(),
		"restaurantDeliveryTime":"41",
		"foodImgSrc":foodImgSrc,
		"foodTitle":foodTitle,
		"foodPrice":foodPrice
	}
	recommendedItems.push(foodItem)
});

JSON.stringify(recommendedItems);
