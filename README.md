# Tip_App
Android Mobile Tip App

# Version 1
Current build of Tipper houses several features to ease the calculation of tips for users.

![Image of tipper app with features shown](https://github.com/jvarCS/Tip_App/blob/main/app/Images/app_v1.png)
### Base/Bill Amount
Edit text component where user will enter their total bill amount. This value will be used to calculate tip based off of tip percentage chosen.

### Tip SeekBar
Slider bar to enable easy selection of a tip percentage between 0-30%. Upon initial interaction with the slidebar, text will appear beneath it reflecting the tip value with a corresponding color. For example, a 1% tip will say "Dog water" in red text to signify poor service. A 30% tip will say "Yaurr" in green text to signify great service. The chosen value will be used with the base amount to determine exact tip value.

### Split Check
Tapping the this checkbox will allow the user to choose from multiple pre-chosen options to split the bill by. There is also a "Custom" option that will allow the user to enter a custom number to split the bill by. Once a number is chosen, the final total will be divided by the chosen number.

### Tip/Total
Once the bill amount, tip percentage, and if checked, the number to split the bill by, are chosen, the calculated tip and total including the tip will be displayed for the user.

### Known bugs
#1. Currently if a user checks the split check, selects the "custom" option, and begins entering the bill amount before entering the custom split number, the app will crash. Probable cause of crash is the empty value of the custom option. Tip calculations begin once user starts entering base amount and there are no checks to ensure the custom option is not empty. Potential fix sees checks added to ensure non-empty custom option is not used in calculations.

### Fixed bugs
#1. App no longer crashes
