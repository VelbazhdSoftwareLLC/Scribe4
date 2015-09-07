package eu.veldsoft.scribe4.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class Glyphs {
  public static final Map<String, Set<XY>> ALL_GLYPHS = new LinkedHashMap<String, Set<XY>>();

  static {
    addSingle();
    addDouble();
    addLine();
    addPipe();
    addSquatT();
    addFourBlock();
    addT();
    addCross();
    addSixBlock();
    addBomber();
    addChair();
    addJ();
    addEarring();
    addHouse();
    addH();
    addU();
    addOttoman();
    addO();
    addNineBlock();
  }

  private static void addSingle() {
    Set<XY> single = new HashSet<XY>();
    single.add(XY.at(0, 0));
    ALL_GLYPHS.put("Single", single);
  }

  private static void addDouble() {
    Set<XY> doubleGlyph = new HashSet<XY>();
    doubleGlyph.add(XY.at(0, 0));
    doubleGlyph.add(XY.at(1, 0));
    ALL_GLYPHS.put("Double", doubleGlyph);
  }

  private static void addLine() {
    Set<XY> line = new HashSet<XY>();
    line.add(XY.at(0, 0));
    line.add(XY.at(1, 0));
    line.add(XY.at(2, 0));
    ALL_GLYPHS.put("Line", line);
  }

  private static void addPipe() {
    Set<XY> pipe = new HashSet<XY>();
    pipe.add(XY.at(0, 1));
    pipe.add(XY.at(1, 1));
    pipe.add(XY.at(2, 1));
    pipe.add(XY.at(2, 0));
    ALL_GLYPHS.put("Pipe", pipe);
  }

  private static void addSquatT() {
    Set<XY> squatT = new HashSet<XY>();
    squatT.add(XY.at(0, 0));
    squatT.add(XY.at(1, 0));
    squatT.add(XY.at(2, 0));
    squatT.add(XY.at(1, 1));
    ALL_GLYPHS.put("Squat T", squatT);
  }

  private static void addFourBlock() {
    Set<XY> fourBlock = new HashSet<XY>();
    fourBlock.add(XY.at(0, 0));
    fourBlock.add(XY.at(1, 0));
    fourBlock.add(XY.at(0, 1));
    fourBlock.add(XY.at(1, 1));
    ALL_GLYPHS.put("Four Block", fourBlock);
  }

  private static void addT() {
    Set<XY> t = new HashSet<XY>();
    t.add(XY.at(0, 0));
    t.add(XY.at(1, 0));
    t.add(XY.at(1, 1));
    t.add(XY.at(1, 2));
    t.add(XY.at(2, 0));
    ALL_GLYPHS.put("T", t);
  }

  private static void addCross() {
    Set<XY> cross = new HashSet<XY>();
    cross.add(XY.at(1, 0));
    cross.add(XY.at(1, 1));
    cross.add(XY.at(1, 2));
    cross.add(XY.at(0, 1));
    cross.add(XY.at(2, 1));
    ALL_GLYPHS.put("Cross", cross);
  }

  private static void addSixBlock() {
    Set<XY> sixBlock = new HashSet<XY>();
    sixBlock.addAll(XY.allXYs());
    sixBlock.remove(XY.at(0, 2));
    sixBlock.remove(XY.at(1, 2));
    sixBlock.remove(XY.at(2, 2));
    ALL_GLYPHS.put("Six Block", sixBlock);
  }

  private static void addBomber() {
    Set<XY> bomber = new HashSet<XY>();
    bomber.addAll(XY.allXYs());
    bomber.remove(XY.at(0, 1));
    bomber.remove(XY.at(0, 2));
    bomber.remove(XY.at(1, 2));
    ALL_GLYPHS.put("Bomber", bomber);
  }

  private static void addChair() {
    Set<XY> chair = new HashSet<XY>();
    chair.addAll(XY.allXYs());
    chair.remove(XY.at(0, 0));
    chair.remove(XY.at(1, 0));
    chair.remove(XY.at(1, 2));
    ALL_GLYPHS.put("Chair", chair);
  }

  private static void addJ() {
    Set<XY> j = new HashSet<XY>();
    j.addAll(XY.allXYs());
    j.remove(XY.at(0, 0));
    j.remove(XY.at(1, 0));
    j.remove(XY.at(1, 1));
    ALL_GLYPHS.put("J", j);
  }

  private static void addEarring() {
    Set<XY> earring = new HashSet<XY>();
    earring.addAll(XY.allXYs());
    earring.remove(XY.at(0, 0));
    earring.remove(XY.at(1, 1));
    ALL_GLYPHS.put("Earring", earring);
  }

  private static void addHouse() {
    Set<XY> house = new HashSet<XY>();
    house.addAll(XY.allXYs());
    house.remove(XY.at(0, 0));
    house.remove(XY.at(2, 0));
    ALL_GLYPHS.put("House", house);
  }

  private static void addH() {
    Set<XY> h = new HashSet<XY>();
    h.addAll(XY.allXYs());
    h.remove(XY.at(1, 0));
    h.remove(XY.at(1, 2));
    ALL_GLYPHS.put("H", h);
  }

  private static void addU() {
    Set<XY> u = new HashSet<XY>();
    u.addAll(XY.allXYs());
    u.remove(XY.at(1, 0));
    u.remove(XY.at(1, 1));
    ALL_GLYPHS.put("U", u);
  }

  private static void addOttoman() {
    Set<XY> ottoman = new HashSet<XY>();
    ottoman.addAll(XY.allXYs());
    ottoman.remove(XY.at(1, 2));
    ALL_GLYPHS.put("Ottoman", ottoman);
  }

  private static void addO() {
    Set<XY> o = new HashSet<XY>();
    o.addAll(XY.allXYs());
    o.remove(XY.at(1, 1));
    ALL_GLYPHS.put("O", o);
  }

  private static void addNineBlock() {
    Set<XY> nineBlock = new HashSet<XY>();
    nineBlock.addAll(XY.allXYs());
    ALL_GLYPHS.put("Nine Block", nineBlock);
  }
}
