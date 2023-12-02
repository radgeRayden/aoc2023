using import Array radl.IO.FileStream

try
    f := FileStream "01a.txt" FileMode.Read
    local digits : (Array i8)

    vvv print
    fold (result = 0) for l in ('lines f)
        'clear digits
        for c in l
            if (c >= char"0" and c <= char"9")
                'append digits (c - char"0")
        if ((countof digits) > 0)
            result + ((digits @ 0) * 10) + ('last digits)
        else result
else ()
