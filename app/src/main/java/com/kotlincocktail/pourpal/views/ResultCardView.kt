package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.kotlincocktail.pourpal.entity.Cocktail
import com.kotlincocktail.pourpal.R
import com.kotlincocktail.pourpal.ui.theme.Black
import com.kotlincocktail.pourpal.ui.theme.DarkBlue
import com.kotlincocktail.pourpal.ui.theme.DarkRed
import com.kotlincocktail.pourpal.ui.theme.LightGray
import com.kotlincocktail.pourpal.ui.theme.Red
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultCardView(
//    cocktails: List<Cocktail>
) {
    val cocktails = listOf(
            Cocktail(
                cocktail_id = 1,
                cocktail_name = "マルガリータ",
                cocktail_name_english = "Margarita",
                base_name = "テキーラ",
                technique_name = "シェイク",
                taste_name = "サワー",
                style_name = "ショート",
                alcohol = 15,
                top_name = "ライム",
                glass_name = "カクテルグラス",
                type_name = "クラシック",
                cocktail_digest = "フレッシュなライムの香りが特徴",
                cocktail_desc = "テキーラベースの代表的なカクテル",
                recipe_desc = "テキーラ、トリプルセック、ライムジュースをシェイク",
                cocktail_img = "margarita.jpg"
            ),
    Cocktail(
        cocktail_id = 2,
        cocktail_name = "モヒート",
        cocktail_name_english = "Mojito",
        base_name = "ラム",
        technique_name = "ビルド",
        taste_name = "スウィート",
        style_name = "ロング",
        alcohol = 10,
        top_name = "ミント",
        glass_name = "ハイボールグラス",
        type_name = "リフレッシング",
        cocktail_digest = "ミントの爽やかさが魅力",
        cocktail_desc = "ラムベースの爽やかなカクテル",
        recipe_desc = "ラム、ミント、砂糖、ソーダ水をミックス",
        cocktail_img = "mojito.jpg"
    )
    // さらにダミーデータを追加する場合は、このようにリストに追加していきます
    )
    val pagerState = rememberPagerState(pageCount = { cocktails.size })
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize(),
        drawerContent = {
            Box(modifier = Modifier
                .padding(start = 300.dp, top = 80.dp)
                .clip(
                    RoundedCornerShape(
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                    )
                )
                .width(60.dp)
                .height(60.dp)
                .background(Red)
            )
            Column(
                Modifier
                    .padding(top = 80.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 16.dp,
                        )
                    )
                    .height(680.dp)
                    .width(300.dp)
                    .background(Red)
                    .padding(40.dp)
            ) {// TODO リスト表示場所
                LazyColumn(content = {
                    itemsIndexed(items = cocktails ) {index, cocktail ->
                        Text(
                            fontSize = 25.sp,
                            text = cocktail.cocktail_name,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable{
                                    coroutineScope.launch {
                                        drawerState.close()
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                        HorizontalDivider(color = DarkRed)
                    }
                })
            }
        },
        drawerState = drawerState
    ){
        Box(
            modifier = Modifier.background(DarkBlue).fillMaxSize(),
            contentAlignment = Center
        ){
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                state = pagerState,
                pageSpacing = (20.dp),
                contentPadding = PaddingValues(horizontal = 60.dp)
            ) { page ->
                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                CardContent(
                    resultString = cocktails[page].cocktail_name,
                    modifier = Modifier
                        .height((680 - (pageOffset * 60)).dp)
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                )
            }
        }
        Box(modifier = Modifier
            .padding(top = 80.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                )
            )
            .width(60.dp)
            .height(60.dp)
            .background(Red)
            .clickable {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
    }
}

@Composable
fun CardContent(resultString: String, modifier:Modifier) {//TODO　カード表示の場所
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Black),
    ) {
        Box {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.templatecocktail),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(CenterHorizontally),
                        contentScale = ContentScale.FillWidth
                    )
                    Row(
                        Modifier
                            .fillMaxWidth(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(text = "作り方", fontSize = 20.sp, modifier = Modifier.align(Bottom))
                        Spacer(modifier = Modifier.weight(1f))
                        for (i in 0..2){
                            Column(modifier = Modifier.width(52.dp), horizontalAlignment = CenterHorizontally) {
                                Image(painter = painterResource(id = R.drawable.loading_icon), contentDescription = "")
                                Text(text = "ジン",textAlign = TextAlign.Center, modifier = Modifier.padding(4.dp), fontSize = 10.sp)
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .padding(top = 4.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState())) {
                        Text(text = "ダミーテキストですここにテキストが入ります。拙者親方と申すは、お立会の中に御存じのお方もござりましょうが、お江戸を発って二十里上方、相州小田原一色町をお過ぎなされて、青物町を上りへお出でなさるれば、欄干橋虎屋藤右衛門、只今では剃髪致して圓斎と名乗りまする。 元朝より大晦日まで御手に入れまする此の薬は、昔、珍の国の唐人外郎と云う人、我が朝へ来たり。 帝へ参内の折から、此の薬を深く込め置き、用ゆる時は一粒ずつ冠の隙間より取り出だす。 依ってその名を帝より「透頂香」と賜る。 即ち文字には、頂き、透く、香と書いて「とうちんこう」と申す。 只今では此の薬、殊の外世上に広まり、方々に似看板を出だし、イヤ小田原の、灰俵の、さん俵の、炭俵のと、色々に申せども、平仮名を以って「ういろう」と致したは、親方圓斎ばかり。もしやお立会の中に、熱海か塔ノ沢へ湯治にお出でなさるるか、又は伊勢御参宮の折からは、必ず門違いなされまするな。 御上りなれば右の方、御下りなれば左側、八方が八つ棟、面が三つ棟玉堂造、破風には菊に桐の薹の御紋を御赦免あって、系図正しき薬でござる。 イヤ最前より家名の自慢ばかり申しても、御存じない方には、正真の胡椒の丸呑み、白河夜船。されば一粒食べかけて、その気味合いをお目に懸けましょう。先ず此の薬を斯様に一粒舌の上に乗せまして、腹内へ納めますると、イヤどうもいえぬは、 いかん肺肝[注釈 2]が健やかに成って、薫風喉より来たり、口中微涼を生ずるが如し。魚、鳥、木の子、麺類の食い合わせ、其の外万病即効あること神の如し。 さて此の薬、第一の奇妙には、舌の廻ることが銭ごまが裸足で逃げる。ひょっと舌が廻り出すと、矢も盾も堪らぬじゃ。 そりゃそりゃそりゃ、そりゃそりゃ、廻って来たわ、廻って来るわ。アワヤ喉、サタラナ舌にカ牙サ歯音、ハマの二つは唇の軽重開口爽やかに、アカサタナ、ハマヤラワ、オコソトノ、ホモヨロヲ。一ツぺぎへぎに、へぎ干し、はじかみ。盆豆、盆米、盆牛蒡、摘蓼、摘豆、摘山椒、書写山の社僧正。 小米の生噛み、小米の生噛み、こん小米のこ生噛み。繻子緋繻子、繻子繻珍。 親も嘉兵衛、子も嘉兵衛、親嘉兵衛子嘉兵衛、子嘉兵衛親嘉兵衛。古栗の木の古切口。雨合羽が番合羽か。貴様の脚絆も皮脚絆、我等が脚絆も皮脚絆。尻皮袴のしっ綻びを、三針針長にちょと縫うて、縫うてちょとぶん出せ。河原撫子野石竹。野良如来野良如来、三野良如来に六野良如来。 一寸の[注釈 3]お小仏にお蹴躓きゃるな、細溝にどじょにょろり。京の生鱈、奈良、生学鰹、ちょと四五貫目。お茶立ちょ、茶立ちょ、ちゃっと立ちょ、茶立ちょ。青竹茶筅でお茶ちゃと立ちゃ。来るわ来るわ何が来る、高野の山のおこけら小僧、狸百匹、箸百膳、天目百杯、棒八百本。武具、馬具、武具馬具、三武具馬具、合わせて武具馬具、六武具馬具。 菊、栗、菊栗、三菊栗、合わせて菊栗、六菊栗。麦、ごみ、麦ごみ、三麦ごみ[注釈 4]、合わせて麦ごみ、六麦ごみ。あの長押の長薙刀は誰が長薙刀ぞ。向こうの胡麻殻は荏の胡麻殻か真胡麻殻か、あれこそ本の真胡麻殻。がらぴぃがらぴぃ風車。起きゃがれ小法師、起きゃがれ小法師、昨夜も溢してまた溢した。 たぁぷぽぽ、たぁぷぽぽ、ちりからちりから、つったっぽ。たっぽたっぽ干蛸[注釈 5]落ちたら煮て食を。煮ても焼いても食われぬ物は、五徳、鉄灸、金熊童子に、石熊、石持、虎熊、虎鱚。中にも東寺の羅生門には、茨木童子が腕栗五合掴んでおむしゃるかの頼光の膝元去らず。鮒、金柑、椎茸、定めて後段な、蕎麦切り、素麺、饂飩か愚鈍なこ新発知。小棚の小下に、小桶にこ味噌がこ有るぞ、こ杓子こ持って、こすくてこ寄こせ。 おっと合点だ、心得田圃の川崎、神奈川、程ヶ谷、戸塚は走って行けば、灸を擦りむく、三里ばかりか、藤沢、平塚、大磯がしや、小磯の宿を七つ起きして、早天そうそう、相州小田原、透頂香。隠れござらぬ貴賎群衆の、花の御江戸の花ういろう。 あれ、あの花を見て、お心をお和らぎゃっという。産子、這子に至るまで、此のういろうの御評判、御存じないとは申されまいまいつぶり、角出せ、棒出せ、ぼうぼう眉に、臼、杵、擂鉢、ばちばち、ぐゎらぐゎらぐゎら[注釈 6]と、羽目を外して今日御出での何茂様に、上げねばならぬ、売らねばならぬと、息せい引っ張り、東方世界の薬の元締、薬師如来も照覧あれと。ホホ敬って、ういろうはいらっしゃりませぬか。")
                    }
                }
            }
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        width = 2.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(Black)
                    .padding(8.dp),
                text = "ジントニック"
            )
        }
    }
}