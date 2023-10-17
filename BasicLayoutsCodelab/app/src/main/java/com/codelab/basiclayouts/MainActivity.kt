/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.basiclayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.codelab.basiclayouts.ui.theme.typography


/*
Basics layouts in compose

Jetpack compose is a UI Toolkit - makes it easy to implement UI designs

Compose:
 DEV - describe how we want to our UI to look (base on the state)
 Compose - responsible of drawing it

Previous codelab:
- How to implement simple layouts using Surfaces, Rows and Columns.
- How to augment: these layouts with modifiers: Padding, fillMaxWidth, and size.

Concepts to be learned:
- Modifiers to augment(ˌôɡˈment) your composables.
- Standard  layout components: Column and LazyRow: How to position size composables.
- Alignments and arrangaments: They change position of child composable within their respective parent
- Material Composables to create comprehensive layouts:
    - Scaffold
    - Bottom Navigation
- Flexible composables using Slot API's
- Build Layouts for different screen configurations.

 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySootheTheme {
                MySootheApp()
            }
        }
    }
}

/*
START WITH A PLAN

1.- Analyze the design itself.
2.- Split the UI into multiple reusable parts from  the Ux UI template design

example:
-UI Screen
    - Screen content
    - Bottom navigation
3. Continue drilling down
-UI Screen
    - Screen content
        - Search bar
        - 'align your body' section (scrollable row)
            - 'align your body' element
        - 'Favorites collection' section ( scrollable grid)
            -'Favorites collection' element
    - Bottom navigation
 */



// Step: Search bar - Modifiers
/*

This composable function accepts a modifier parameter and passes this on to the textfield
composable function

 This is a best practice as per Compose guidelines.

 This allows the method's caller to modify the composable's look and feel, which makes it more
 flexible and reusable.

 */

/*
Modifiers:

They are used to:

1.- Change attrs like:
    - size,
    - layout,
    - behavior,
    - appearance.
2.- Add information, like accessibility labels.
3.- To Process user input.
4.- To  high-level interactions:
    - making an element clickable,
    - scrollable,
    - draggable,
    - zoomable.


- Each composable has a modifier parameter -> to adapt that composable's look, feel and behavior.
- When setting the modifier -> possible to chain multiple modifier methods to create a more complex adaptation.
    modifier
        .method1(...)...
        .method2(...)...
        .methodN(...)...

- There's a list of modifiers
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )// Icon composable ( it is overloaded) this one receives ImageVector Object
        },
        colors = TextFieldDefaults.colors( // defaults for the text field shape, height, width, thickness
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ) ,
        placeholder = { // we can call stringResource composable function to get the string based on the Id
            Text(
                stringResource(id = R.string.placeholder_search),
                style = typography.bodySmall

            )
        },
        modifier = modifier
            .fillMaxWidth() // uses all horizontal space of its parent
            .heightIn(min = 56.dp), // to establish a minimum height

    )
}

// Step: Align your body - Alignment
/*
Alignment

- Column to position elements underneath each other
- Composable has to be reusable: Extract hardcoded attrs an put them as parameters so the composable
will be dynamic.

- modify width and height: There are several modifier methods
    - size: preferred size of the content to be exactly size dp square
- clip:
    - clip the content to shape: It requires a Shape object one predefined is Circular shape

- Alignment concept

1 D - start  center end
=>
Column: Vertical dimension - horizontal alignment:
    - Start
    - CenterHorizontally
    - End

Row: Horizontal dimension - vertical alignment:
    - Top
    - CenterVertically
    - Bottom

Box: 2d, cross product of column and row  3 X 3
    TopStart
    TopCenter
    TopEnd
    CenterStart
    Center
    CenterEnd
    BottomStart
    BottomCenter
    BottomEnd


YOU define aignment in a container
 - > Children follow alignment
    -> Want to override it ? Then call modifier.align() to override behavior

Scales:
    It represents a rule to apply to scale a source rectangle
    so the it gets inscribed into a destination.
    - crop: scale the source mantaining aspect ratio so that source dimensions  >= destination
    - fit: scale the source mantaining aspect ratio so that source dimensions  <= destination
    - fillbounds: scale source (NOT maintaining aspect ratio) to fill destination bounds.

 It's considered a best practice among designers to align text elements based on their baseline,
 instead of their top or bottom.

 .paddingFromBaseline(top = 24.dp, bottom = 8.dp)

*/

@Composable
fun AlignYourBodyElement(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    @StringRes text: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id =drawable),
            contentDescription = null,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 24.dp, bottom = 8.dp)

        )

    }

}

// Step: Favorite collection card - Material Surface
/*

 */
@Composable
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    @StringRes text: Int
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painter = painterResource(id = drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = stringResource(id = text),
                style = typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// Step: Align your body row - Arrangements
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
    // Implement composable here
}

// Step: Favorite collections grid - LazyGrid
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    // Implement composable here
}

// Step: Home section - Slot APIs
@Composable
fun HomeSection(
    modifier: Modifier = Modifier
) {
    // Implement composable here
}

// Step: Home screen - Scrolling
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: Bottom navigation - Material
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: MySoothe App - Scaffold
@Composable
fun MySootheAppPortrait() {
    // Implement composable here
}

// Step: Bottom navigation - Material
@Composable
private fun SootheNavigationRail(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: Landscape Mode
@Composable
fun MySootheAppLandscape(){
    // Implement composable here
}

// Step: MySoothe App
@Composable
fun MySootheApp() {
    SearchBar(Modifier.padding(8.dp))
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F0EE,
    widthDp = 320,
    heightDp = 56
)
@Composable
fun SearchBarPreview() {
    MySootheTheme {
        SearchBar(Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            modifier = Modifier.padding(8.dp),
            drawable = R.drawable.ab1_inversions,
            text = R.string.ab1_inversions,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            modifier = Modifier.padding(8.dp),
            R.drawable.fc2_nature_meditations,
            R.string.fc2_nature_meditations
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun HomeSectionPreview() {
    MySootheTheme { HomeSection() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun NavigationRailPreview() {
    MySootheTheme { SootheNavigationRail() }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePortraitPreview() {
    MySootheAppPortrait()
}

@Preview(widthDp = 640, heightDp = 360)
@Composable
fun MySootheLandscapePreview() {
    MySootheAppLandscape()
}
