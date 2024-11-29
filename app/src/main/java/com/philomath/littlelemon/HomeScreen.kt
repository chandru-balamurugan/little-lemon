package com.philomath.littlelemonimport android.R.attr.bottomimport android.R.attr.textimport android.graphics.drawable.Drawableimport android.widget.ImageViewimport androidx.compose.foundation.Imageimport androidx.compose.foundation.backgroundimport androidx.compose.foundation.borderimport androidx.compose.foundation.clickableimport androidx.compose.foundation.layout.Arrangementimport androidx.compose.foundation.layout.Boximport androidx.compose.foundation.layout.Columnimport androidx.compose.foundation.layout.Rowimport androidx.compose.foundation.layout.Spacerimport androidx.compose.foundation.layout.fillMaxHeightimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.heightimport androidx.compose.foundation.layout.paddingimport androidx.compose.foundation.layout.sizeimport androidx.compose.foundation.layout.widthimport androidx.compose.foundation.layout.wrapContentHeightimport androidx.compose.foundation.layout.wrapContentWidthimport androidx.compose.foundation.rememberScrollStateimport androidx.compose.foundation.shape.CircleShapeimport androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.foundation.verticalScrollimport androidx.compose.material.icons.Iconsimport androidx.compose.material.icons.filled.Searchimport androidx.compose.material3.Cardimport androidx.compose.material3.CardDefaultsimport androidx.compose.material3.Iconimport androidx.compose.material3.Textimport androidx.compose.material3.TextFieldimport androidx.compose.material3.TextFieldDefaultsimport androidx.compose.runtime.Composableimport androidx.compose.runtime.LaunchedEffectimport androidx.compose.runtime.MutableStateimport androidx.compose.runtime.getValueimport androidx.compose.runtime.mutableStateOfimport androidx.compose.runtime.rememberimport androidx.compose.runtime.setValueimport androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.draw.clipimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.layout.ContentScaleimport androidx.compose.ui.platform.LocalContextimport androidx.compose.ui.res.painterResourceimport androidx.compose.ui.res.stringResourceimport androidx.compose.ui.text.TextStyleimport androidx.compose.ui.text.font.FontStyleimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.ui.text.input.TextFieldValueimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport androidx.compose.ui.viewinterop.AndroidViewimport androidx.navigation.NavHostControllerimport com.bumptech.glide.Glideimport com.bumptech.glide.integration.compose.GlideImageimport com.bumptech.glide.load.engine.DiskCacheStrategyimport com.bumptech.glide.request.RequestOptionsimport com.bumptech.glide.request.target.CustomTargetimport com.philomath.littlelemon.ui.theme.greenimport com.philomath.littlelemon.ui.theme.yellowimport com.philomath.littlelemon.ui.theme.cloud@Composablefun HomeScreen(navController: NavHostController) {      val context = LocalContext.current      val db = AppDatabase.invoke(context)      val menuList = remember { mutableStateOf<List<MenuItem>>(emptyList()) }      // Using LaunchedEffect to perform the database operation asynchronously      LaunchedEffect(Unit) {            // Fetch the menu items in the background using the DAO            val items = db.menuItemDao().getAllMenuItems() // Suspended function            menuList.value = items      }      Column {            AppBar(navController)            HeroSection()            Box(                  modifier = Modifier.padding(                        top = 12.dp,                        bottom = 16.dp,                        start = 12.dp,                        end = 12.dp                  )            ) {                  MenuItemsColumn(menuList)            }      }}@Composablefun AppBar(navController: NavHostController) {      Row(            horizontalArrangement = Arrangement.Center,            verticalAlignment = Alignment.CenterVertically,            modifier = Modifier                  .padding(12.dp)                  .fillMaxWidth()      ) {            Box(                  modifier = Modifier.fillMaxWidth(0.6f),                  contentAlignment = Alignment.CenterEnd            ) {                  Image(                        painter = painterResource(id = R.drawable.logo),                        contentDescription = "Little Lemon"                  )            }            Box(                  modifier = Modifier.fillMaxWidth(1f),                  contentAlignment = Alignment.CenterEnd            ) {                  Image(                        modifier = Modifier                              .clickable {                                    navController.navigate(Profile.route)                              }                              .size(40.dp)                              .clip(CircleShape)                              .border(1.dp, Color.Transparent, CircleShape),                        painter = painterResource(id = R.drawable.profile),                        contentDescription = "Little Lemon"                  )            }      }}@Composablefun HeroSection() {      var searchText by remember { mutableStateOf(TextFieldValue("")) }      val imageModifier = Modifier            .size(150.dp)//            .aspectRatio(10f/9f)            .clip(RoundedCornerShape(12.dp))      Box(            Modifier                  .background(color = green)                  .fillMaxWidth()                  .padding(12.dp)      ) {            Column(modifier = Modifier.fillMaxWidth()) {                  Text(text = stringResource(R.string.app_name), color = yellow, fontSize = 40.sp)                  Row(                        modifier = Modifier.fillMaxWidth(),                        horizontalArrangement = (Arrangement.SpaceBetween)                  ) {                        Column(modifier = Modifier.fillMaxWidth(0.5f)) {                              Text(                                    text = stringResource(R.string.app_city),                                    color = cloud,                                    fontSize = 32.sp                              )                              Text(                                    modifier = Modifier.padding(top = 12.dp),                                    text = stringResource(R.string.app_description),                                    color = cloud,                                    fontSize = 12.sp                              )                        }                        Image(                              modifier = imageModifier,                              painter = painterResource(R.drawable.hero_image),                              contentDescription = "Hero Image",                              contentScale = ContentScale.Crop                        )                  }                  Spacer(Modifier.height(14.dp))//                  Box(//                        modifier = Modifier//                              .background(cloud)//                              .wrapContentWidth()//                              .wrapContentHeight()//                  ) {                  TextField(                        shape = RoundedCornerShape(12.dp),                        colors = TextFieldDefaults.colors(cloud),                        value = searchText,                        leadingIcon = {                              Icon(                                    imageVector = Icons.Default.Search,                                    contentDescription = null                              )                        },                        label = { Text(text = "Enter search phrase") },                        onValueChange = { searchText = it },                        modifier = Modifier.fillMaxWidth()                  )//                  }            }      }}@Composablefun MenuItemsColumn(menuList: MutableState<List<MenuItem>>) {      Column(modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())) {            menuList.value.forEach { itm -> MenuItemCard(itm) }      }}@Composablefun MenuItemCard(itm: MenuItem) {      Card(//            shape = CardDefaults.cardS,//            elevation = CardDefaults.cardElevation(//                  defaultElevation = 8.dp//            ),            modifier = Modifier                  .height(150.dp)                  .fillMaxWidth()                  .padding(vertical = 8.dp)      ) {            Row {                  Column(modifier = Modifier                        .fillMaxWidth(0.5f)                        .padding(12.dp)) {                        Text(text = itm.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)                        Spacer(modifier = Modifier.height(6.dp))                        Text(                              text = itm.description,                              fontSize = 12.sp,                              maxLines = 3,                              fontWeight = FontWeight.Light                        )                        Spacer(modifier = Modifier.height(6.dp))                        Text(text = "$${itm.price}", fontSize = 14.sp, fontWeight = FontWeight.W400)                  }                  Box(modifier = Modifier.fillMaxWidth()) {                        GlideImage(                              url = "${itm.image}", // Image URL                              modifier = Modifier                                    .fillMaxWidth()                                    .fillMaxHeight(),                              contentDescription = itm.title,                        )                  }            }      }}@Composablefun GlideImage(      url: String,      modifier: Modifier = Modifier,      contentDescription: String? = null,      contentScale: ContentScale = ContentScale.Crop) {      val context = LocalContext.current      // Using AndroidView to embed ImageView and load image via Glide      AndroidView(            factory = { ctx ->                  ImageView(ctx).apply {                        Glide.with(ctx)                              .load(url) // URL of the image to load                              .diskCacheStrategy(DiskCacheStrategy.ALL) // Image caching strategy                              .centerCrop() // Set the content scale                              .into(this) // Load image into ImageView                  }            },            modifier = modifier,            update = { view ->                  // Update the image if necessary                  Glide.with(context)                        .load(url)                        .diskCacheStrategy(DiskCacheStrategy.ALL)                        .centerCrop()                        .into(view)            }      )}