using import Array Box enum Map Option print radl.IO.FileStream slice String struct

global numerals =
    arrayof String
        "zero"
        "one"
        "two"
        "three"
        "four"
        "five"
        "six"
        "seven"
        "eight"
        "nine"

# have a trie, because ur epic
enum Trie
    Branch : (children = (Map i8 this-type))
    Terminal : (value = i8)

    inline __typecall (cls)
        local b = (this-type.Branch)
        for v num in (enumerate numerals)
            fn traverse (node str value)
                returning void
                viewing node
                node := 'unsafe-extract-payload node this-type.Branch.Type

                if ((countof str) > 0)
                    c := str @ 0
                    local new-node = (this-type.Branch)
                    this-function ('setdefault node c new-node) (rslice str 1) value
                    ()
                else
                    'set node 0:i8 (this-type.Terminal value)
            traverse b (imply num Slice) (v as i8)
        b

global t : (Box Trie) # you know where it's stored

fn digit-name-match (str)
    loop (next i = (t as Trie) 0)
        dispatch next
        case Branch (children)
            try ('get children 0:i8)
            then (terminal)
                return true (deref ('unsafe-extract-payload terminal i8))
            else ()

            if (i >= (countof str))
                return false 0:i8

            c := str @ i
            try ('get children c)
            then (next)
                _ next (i + 1)
            else
                return false 0:i8
        default (abort)

fn digit-match (str)
    if ((countof str) > 0)
        loop (idx = 0)
            if (idx >= (countof str))
                break false 0:i8

            c := str @ idx
            if (c >= char"0" and c <= char"9")
                return true (c - char"0")
            else
                match? value := digit-name-match (rslice (view str) idx)
                if match?
                    return true value
                else
                    idx + 1
    else
        _ false 0:i8

try
    f := FileStream "01a.txt" FileMode.Read
    local digits : (array i8 2)

    vvv print
    fold (result = 0) for l in ('lines f)
        match? first-digit := digit-match l
        if (not match?) # empty or (invalid?) line
            result
        else
            vvv bind second-digit
            fold (digit = first-digit) for i in (rrange (countof l))
                match? second-digit := digit-match (rslice (view l) i)
                if match?
                    break second-digit
                else
                    digit
            result + ((first-digit * 10) + second-digit)
else ()
()
