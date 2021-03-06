package com.netcracker.courses.oop.console;

import com.netcracker.courses.oop.console.utils.AdvancedConsolePrinter;
import com.netcracker.courses.oop.console.utils.ConsoleScanner;
import com.netcracker.courses.oop.console.utils.DefaultConsoleScanner;
import com.netcracker.courses.oop.console.utils.DefaultMusicPrinter;
import com.netcracker.courses.oop.music.MusicGenre;
import com.netcracker.courses.oop.music.digital.MusicCD;
import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;
import com.netcracker.courses.oop.music.digital.composition.CompressedComposition;
import com.netcracker.courses.oop.music.digital.composition.DigitalCompositionFormat;
import com.netcracker.courses.oop.music.digital.composition.LossLessComposition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Console class which is both model and controller.
 * Enables user to interact with console using keyword + (appropriate) command
 */
public class ConsoleController {

    /* messages */
    private final static String GREETINGS_MESSAGE    = "Welcome to the Ultimate CD Burner!";
    private static final String BYE_MESSAGE          = "Good bye!";

    private final static String HELP_MESSAGE;

    /* initializes HELP_MESSAGE */
    static {

        InputStream inputStream = ConsoleController.class.getResourceAsStream("/help.help");

        if (inputStream == null) {
            HELP_MESSAGE = "help will be added with future releases";
        } else {
            StringBuilder sb = new StringBuilder();

            try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream))) {
                String line = r.readLine();

                while (line != null) {
                    sb.append(line).append("\n");
                    line = r.readLine();
                }

                sb.setLength(sb.length() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HELP_MESSAGE = sb.toString();
        }
    }


   /* commands */
    private static final String SELECT_COMMAND       = "select";
    private static final String PRINT_COMMAND        = "print";
    private static final String CREATE_COMMAND       = "create";
    private static final String SORT_COMMAND         = "sort";
    private static final String FIND_COMMAND         = "find";

    private static final String EXIT_COMMAND         = "exit";
    private static final String HELP_COMMAND         = "help";



    /* options */
    private static final String SONG_OPTION          = "song";
    private static final String CD_OPTION            = "cd";
    private static final String COMPILATION_OPTION   = "cmpl";


    /* arguments */
    private static final String ALL_ARG              = "--all";
    private static final String BY_NAME_ARG          = "byname";
    private static final String BY_ARTIST_ARG        = "byartist";
    private static final String BY_SIZE_ARG          = "bysize";
    private static final String BY_DURATION_ARG      = "bydur";
    private static final String BY_GENRE_ARG         = "bygenre";

    private static final int                        INIT_CAPACITY = 10;
    private ArrayList<AbstractDigitalComposition>   allSongs;
    private ArrayList<MusicCD>                      allCD;
    private List<AbstractDigitalComposition>        compilation;

    private int                                     selectedSongInd = -1;
    private int                                     selectedCDInd = -1;

    private AdvancedConsolePrinter printer = new DefaultMusicPrinter();
    private ConsoleScanner scanner = new DefaultConsoleScanner();

    public ConsoleController() {
        printer.printMessage(GREETINGS_MESSAGE);

        allCD = new ArrayList<>(INIT_CAPACITY);

        /* i might replace it with reading from file */
        AbstractDigitalComposition[] temp = new AbstractDigitalComposition[] {
                new CompressedComposition(
                        "Sad but True",
                        "Metallica",
                        MusicGenre.METAL,
                        1993,
                        324,
                        CompressedComposition.MAX_BITRATE,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Enter Sandman",
                        "Metallica",
                        MusicGenre.METAL,
                        1991,
                        352,
                        CompressedComposition.MAX_BITRATE,
                        DigitalCompositionFormat.MP3),
                new LossLessComposition(
                        "My World",
                        "Metallica",
                        MusicGenre.METAL,
                        2003,
                        346,
                        DigitalCompositionFormat.FLAC
                ),
                new LossLessComposition(
                        "St. Anger",
                        "Metallica",
                        MusicGenre.METAL,
                        2003,
                        441,
                        DigitalCompositionFormat.FLAC
                ),
                new LossLessComposition(
                        "Sweet Amber",
                        "Metallica",
                        MusicGenre.METAL,
                        2003,
                        428,
                        DigitalCompositionFormat.FLAC
                ),
                new CompressedComposition(
                        "The Emperor",
                        "Paint The Future Black",
                        MusicGenre.ALTERANTIVE,
                        2017,
                        200,
                        320,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Scars on the Face",
                        "Paint The Future Black",
                        MusicGenre.ALTERANTIVE,
                        2017,
                        205,
                        320,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Less Than Three",
                        "Paint The Future Black",
                        MusicGenre.ALTERANTIVE,
                        2017,
                        242,
                        320,
                        DigitalCompositionFormat.MP3),
                new LossLessComposition(
                        "Let's Go",
                        "Stuck in the Sound",
                        MusicGenre.INDIE,
                        2012,
                        271,
                        DigitalCompositionFormat.WAV
                ),
                new CompressedComposition(
                        "Move",
                        "Saint Motel",
                        MusicGenre.INDIE,
                        2016,
                        307,
                        240,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Slow Motion",
                        "Saint Motel",
                        MusicGenre.INDIE,
                        2016,
                        236,
                        240,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "You Can Be You",
                        "Saint Motel",
                        MusicGenre.INDIE,
                        2016,
                        237,
                        240,
                        DigitalCompositionFormat.MP3),

        };

        allSongs = new ArrayList<>(Arrays.asList(temp));

        MusicCD cd;

        cd = new MusicCD(70, "small disk");
        cd.addAllCompositions(allSongs);
        allCD.add(cd);

        cd = new MusicCD(300, "large disk");
        cd.addAllCompositions(allSongs);
        allCD.add(cd);
    }

    /**
     * handles provided user command
     * @param userInput command
     * @return false only in case input == "exit"
     */
    public boolean handleUserInput(String userInput) {

        if (userInput == null) {
            printer.printMessage("weird, but input == null");
            return true;
        }

        if (userInput.isEmpty()) {
            return true;
        }

        String input = userInput.toLowerCase();

        StringTokenizer st = new StringTokenizer(input);

        input = st.nextToken();

        if (st.hasMoreTokens()) {
            switch (input) {
                case SELECT_COMMAND: {
                    select(st);
                    break;
                }
                case PRINT_COMMAND: {
                    print(st);
                    break;
                }
                case CREATE_COMMAND: {
                    create(st);
                    break;
                }
                case SORT_COMMAND: {
                    sort(st);
                    break;
                }
                case FIND_COMMAND: {
                    find(st);
                    break;
                }
                default: {
                    printer.printMessage("\"" + userInput + "\" is unsupported command/keyword. "
                            + "use help to see the list of available commands");
                    break;
                }
            }
        } else {
            switch (input) {
                case HELP_COMMAND: {
                    printer.printMessage(HELP_MESSAGE);
                    break;
                }
                case EXIT_COMMAND: {
                    printer.printMessage(BYE_MESSAGE);
                    return false;
                }
                default: {
                    printer.printMessage("\"" + userInput + "\" is unsupported command/keyword. "
                            + "use help to see the list of available commands");
                    break;
                }
            }
        }
        return true;
    }


    private void select(StringTokenizer st) {

        String option = st.nextToken();

        switch (option) {
            case SONG_OPTION: {
                selectedSongInd = scanner.selectIndFromList(allSongs, printer);
                break;
            }
            case CD_OPTION: {
                selectedCDInd = scanner.selectIndFromList(allCD, printer);
                break;
            }
            default: {
                printer.printMessage("\"" + option + "\" is unsupported option for select. "
                        + "use help to see the list of available commands");
                break;
            }
        }
    }

    private void print(StringTokenizer st) {

        String option = st.nextToken();

        String arg = "";
        if (st.hasMoreTokens()) {
            arg = st.nextToken();
        }

        switch (option) {
            case SONG_OPTION: {
                if (ALL_ARG.equals(arg)) printer.printAll(allSongs);
                else {
                    if (selectedSongInd == -1) printer.printMessage("no song is selected");
                    else {
                        System.out.print("\t");
                        printer.print(allSongs.get(selectedSongInd));
                    }
                }
                break;
            }
            case CD_OPTION: {
                if (ALL_ARG.equals(arg)) printer.printAll(allCD);
                else {
                    if (selectedCDInd == -1) printer.printMessage("no CD is selected");
                    else {
                        System.out.print("\t");
                        printer.print(allCD.get(selectedCDInd));
                    }
                }
                break;
            }
            case COMPILATION_OPTION: {
                printer.printAll(compilation);
                break;
            }
            default: {
                printer.printMessage("\"" + option + "\" is unsupported option for print. "
                        + "use help to see the list of available commands");
                break;
            }
        }
    }

    private void create(StringTokenizer st) {

        String option = st.nextToken();

        switch (option) {
            case COMPILATION_OPTION: {
                createCompilation();
                break;
            }
            case CD_OPTION: {
                createCD(st);
                break;
            }
            default: {
                printer.printMessage("\"" + option + "\" is unsupported option for create. "
                        + "use help to see the list of available commands");
                break;
            }
        }
    }

    private void createCompilation() {

        printer.printMessage("choose [l, r] boundaries for compilation:");
        printer.printSelectionList(allSongs);

        int l = scanner.readIntFromConsole("l", printer, (i) -> i < allSongs.size() && i >= 0);
        int r = scanner.readIntFromConsole("r", printer, (i) -> i < allSongs.size() && i >= l);

        compilation = allSongs.subList(l, r + 1);
    }

    private void createCD(StringTokenizer st) {
        if (compilation == null) {
            printer.printMessage("create compilation before creating disk");
            return;
        }

        int sizeArg;
        String nameArg;

        try {
            sizeArg = Integer.parseInt(st.nextToken());
            nameArg = st.nextToken();
        } catch (Exception e) {
            printer.printMessage("invalid arguments for create cd. Use help to learn how to crate CD");
            return;
        }

        MusicCD cd = new MusicCD(sizeArg, nameArg);

        List<AbstractDigitalComposition> temp = cd.addAllCompositions(compilation);

        if (!temp.isEmpty()) {
            printer.printMessage("some compositions from compilation didn't fit on cd."
                    + "select them as compilation? y / n"
            );
        }

        try {
            int ans = System.in.read();
            if (ans == (int) 'y') compilation = temp;
        } catch (IOException e) {
            printer.printMessage("sth wrong with your answer");
        }

        allCD.add(cd);
    }



    private void sort(StringTokenizer st) {

        if (selectedCDInd == -1) {
            printer.printMessage("select CD before sorting it, please");
            return;
        }

        MusicCD selectedCD = allCD.get(selectedCDInd);

        String arg = st.nextToken();

        switch (arg) {
            case BY_NAME_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_NAME);
                break;
            }
            case BY_ARTIST_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_ARTIST);
                break;
            }
            case BY_DURATION_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_DURATION);
                break;
            }
            case BY_GENRE_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_GENRE);
                break;
            }
            case BY_SIZE_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_SIZE);
                break;
            }
            default: {
                /* why not */
                selectedCD.sort(arg.hashCode());
                break;
            }
        }
    }

    private void find(StringTokenizer st) {

        if (!st.hasMoreTokens() || !st.nextToken().equals(SONG_OPTION)) {
            printer.printMessage("You have to specify \"song\" option for find command!");
            return;
        }

        if (selectedCDInd == -1) {
            printer.printMessage("first, select CD");
            return;
        }

        printer.printMessage("specify ranges [minSize, maxSize] and [minYear, maxYear]");

        int minSize = scanner.readIntFromConsole("maxYear", printer, (i) -> i >= 0);
        int maxSize = scanner.readIntFromConsole("maxYear", printer, (i) -> i >= minSize);
        int minYear = scanner.readIntFromConsole("minYear", printer, (i) -> i >= 0);
        int maxYear = scanner.readIntFromConsole("maxYear", printer, (i) -> i >= minYear);


        AbstractDigitalComposition result =
                allCD.get(selectedCDInd).findSong(minSize, maxSize, minYear, maxYear);

        if (result == null) {
            printer.printMessage("nothing found.");
        } else {
            printer.printMessage("found song :");
            System.out.print("\t");
            printer.print(result);
        }
    }
}