# FindYourKid
Test Application that reads from a API and displays a list of busses and the details of them to allow the parents to locate their kids.

## Development

This is an application developed in Android Studio with Kotlin. It is design to support devices from JellyBean (Android OS) and newer.

The application consiste of two main views, the list view and the details view. The list view, implemented with RecyclerView
to maximize efficiency with large sets of data, reads from a JSON the values necessary to paint each element. Every single
one of this elements has a "onClickListener" that allows the user to touch the bus he wants to see more about and starts
the details activity for the user to have more information. The details activity has the details of the bus on the top
section of the screen and a map on the bottom. The map shows a list of markers that indicate the different stops of the bus.

### Details
* It is develop in Kotlin
* Uses gradle for dependency managment
* It follows some directives about UI design (Colors and contrast)
* It has a animation on the first screen to load a logo before going into the application

-------------------------------------------------------------------------------------------
Eeny, meeny, miny, moe. With wich bus should I go?
