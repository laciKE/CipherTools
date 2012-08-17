#!/bin/sh

echo "usage: svg2drawable.sh xhdpi_width xhdpi_height source_destination android_project_destination"

for f in $3/*.svg; do
    filename=`basename ${f} .svg`
  
    width=$1
    height=$2
    rsvg -w ${width} -h ${height} ${f} $4/res/drawable-xhdpi/${filename}.png

    width=`expr 6 \* $1 \/ 8`
    height=`expr 6 \* $2 \/ 8`
    rsvg -w ${width} -h ${height} ${f} $4/res/drawable-hdpi/${filename}.png

    width=`expr 4 \* $1 \/ 8`
    height=`expr 4 \* $2 \/ 8`
    rsvg -w ${width} -h ${height} ${f} $4/res/drawable-mdpi/${filename}.png

    width=`expr 3 \* $1 \/ 8`
    height=`expr 3 \* $2 \/ 8`
    rsvg -w ${width} -h ${height} ${f} $4/res/drawable-ldpi/${filename}.png

done
