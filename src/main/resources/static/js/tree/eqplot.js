// Generated by CoffeeScript 1.9.3
 var options = {
      id: "",
      treestring: "",
      labelcolors: "",
      labelscales: "",
      equallength: true,
      "default": {
        label_fontsize: 1,
        label_color: "#484848",
        label_offset: 1.2,
        edge_width: 0.08,
        edge_color: "#fec44f",
        edge_scale: 1
      } 
  };
(function() {
  $(document).ready(function() {
    var clear, default_options_string, doplot, load_options, plotPhylogeny, reset_options, saveplot;

    default_options_string = JSON.stringify(options, null, "\t");
    reset_options = function() {
      return options = JSON.parse(default_options_string);
    };
    plotPhylogeny = function(options) {
      var _edges, _labels, a, b, bbox, color, drag, dragmove, edge_color, edge_scale, edge_width, edges, fontsize, fx, fy, get_text_size, height, i, j, l, label_color, label_fontsize, label_offset, labelcolors, labels, labelscales, len, len1, len2, len3, len4, line, m, n, nl, nodes, o, outheight, outwidth, p, pad, parse_input, parse_labelcolors, parse_labelscales, q, ref, ref1, ref2, ref3, resolve, svg, tmp, tree, width, x, y;
      ref = options["default"], label_fontsize = ref.label_fontsize, label_color = ref.label_color, label_offset = ref.label_offset, edge_width = ref.edge_width, edge_color = ref.edge_color, edge_scale = ref.edge_scale;
      parse_input = function(s) {
        var d;
        s = s.replace(/\s/g, "");
        d = {};
        if (s !== "") {
          s.split(",").map(function(e) {
            var k, ref1, v;
            ref1 = e.split(":"), k = ref1[0], v = ref1[1];
            return d[k] = v;
          });
        }
        return d;
      };
      parse_labelcolors = parse_input;
      parse_labelscales = function(s) {
        var d, f, k, v;
        d = parse_input(s);
        for (k in d) {
          v = d[k];
          f = parseFloat(v);
          if (isNaN(f) || f <= 0) {
            throw "Invalid label-scale: " + v;
          }
          d[k] = f;
        }
        return d;
      };
      svg = d3.select("\#" + options.id).append("svg").attr("xmlns", "http://www.w3.org/2000/svg").attr("preserveAspectRatio", "xMinYMin meet");
      if (options.equallength) {
        tree = equal_angle(options.treestring, 8 * edge_scale, true);
      } else {
        tree = equal_angle(options.treestring, 20 * edge_scale, false);
      }
      nl = tree.postToList();
      labelcolors = parse_labelcolors(options.labelcolors);
      labelscales = parse_labelscales(options.labelscales);
      a=svg.append("a");
      tmp = a.append("text");
      get_text_size = function(string, fontsize) {
        var height, ref1, width;
        tmp.text(string).attr("font-size", fontsize);
        a.attr("href",string.toLowerCase()+"/")
        ref1 = tmp[0][0].getBoundingClientRect(), width = ref1.width, height = ref1.height;
        return [width, height];
      };
      nodes = [];
      edges = [];
      labels = [];
      for (l = 0, len = nl.length; l < len; l++) {
        n = nl[l];
        if (!n.isRoot()) {
          ref1 = n.loc, x = ref1[0], y = ref1[1];
          ref2 = n.father.loc, fx = ref2[0], fy = ref2[1];
          edges.push([
            {
              x: x,
              y: y
            }, {
              x: fx,
              y: fy
            }
          ]);
          if ((n.name != null) && n.name !== "") {
            fontsize = label_fontsize * (labelscales[n.name] || 1);
            color = labelcolors[n.name] || label_color;
            ref3 = get_text_size(n.name, fontsize), width = ref3[0], height = ref3[1];
            labels.push({
              x: x,
              y: y,
              lx: fx + (x - fx) * label_offset,
              ly: fy + (y - fy) * label_offset,
              name: n.name,
              width: width,
              height: height,
              fontsize: fontsize,
              color: color
            });
          }
        }
      }
      tmp.remove();
      resolve = function(a, b) {
        var b1, b2, hori_overlap, l1, l2, r1, r2, t1, t2, vert_overlap;
        l1 = a.lx - a.width / 2;
        r1 = a.lx + a.width / 2;
        l2 = b.lx - b.width / 2;
        r2 = b.lx + b.width / 2;
        if (r1 < l2 || r2 < l1) {
          return;
        }
        t1 = a.ly - a.height / 3;
        b1 = a.ly + a.height / 3;
        t2 = b.ly - b.height / 3;
        b2 = b.ly + b.height / 3;
        if (b1 <= t2 || b2 <= t1) {
          return;
        }
        if (a.x <= b.x) {
          hori_overlap = r1 - l2;
        } else {
          hori_overlap = r2 - l1;
        }
        if (a.y <= b.y) {
          vert_overlap = b1 - t2;
        } else {
          vert_overlap = b2 - t1;
        }
        if (hori_overlap / (a.width + b.width) < vert_overlap / (a.height + b.height)) {
          if (a.x <= b.x) {
            a.lx -= hori_overlap / 2;
            return b.lx += hori_overlap / 2;
          } else {
            a.lx += hori_overlap / 2;
            return b.lx -= hori_overlap / 2;
          }
        } else {
          if (a.y <= b.y) {
            a.ly -= vert_overlap / 2;
            return b.ly += vert_overlap / 2;
          } else {
            a.ly += vert_overlap / 2;
            return b.ly -= vert_overlap / 2;
          }
        }
      };
      for (i = m = 0, len1 = labels.length; m < len1; i = ++m) {
        a = labels[i];
        for (j = o = 0, len2 = labels.length; o < len2; j = ++o) {
          b = labels[j];
          if (i !== j) {
            resolve(a, b);
          }
        }
      }
      for (i = p = 0, len3 = labels.length; p < len3; i = ++p) {
        a = labels[i];
        for (j = q = 0, len4 = labels.length; q < len4; j = ++q) {
          b = labels[j];
          if (i !== j) {
            resolve(a, b);
          }
        }
      }
      dragmove = function(d) {
        return d3.select(this).attr("x", d3.event.x).attr("y", d3.event.y);
      };
      drag = d3.behavior.drag().on("drag", dragmove);
      line = d3.svg.line().x(function(d) {
        return d.x;
      }).y(function(d) {
        return d.y;
      });
      _edges = svg.append("g").selectAll("path").data(edges).enter().append("path").attr("d", function(d) {
        return line(d);
      }).attr("stroke-width", edge_width).attr("stroke", edge_color).attr("stroke-linejoin", "round").attr("stroke-linecap", "round");
      _labels =svg.append("g").selectAll("a").data(labels).enter().append("a").attr("href", function(d){return d.name.toLowerCase()+"/";}).append("text").attr("x", function(d) {
        return d.lx;
      }).attr("y", function(d) {
        return d.ly;
      }).attr('dy', '0.35em').attr("text-anchor", "middle").attr("font-size", function(d) {
        return d.fontsize;
      }).attr("fill", function(d) {
        return d.color;
      }).text(function(d) {
        return d.name;
      }).call(drag);
      /*
		 * svg.append("g").selectAll("text").data(labels).enter().append("text").attr("x",
		 * function(d) { return d.lx; }).attr("y", function(d) { return d.ly;
		 * }).attr('dy', '0.35em').attr("text-anchor",
		 * "middle").attr("font-size", function(d) { return d.fontsize;
		 * }).attr("fill", function(d) { return d.color; }).text(function(d) {
		 * return d.name; }).call(drag);
		 */
      pad = 5;
      bbox = svg[0][0].getBBox();
      x = bbox.x, y = bbox.y, width = bbox.width, height = bbox.height;
      svg.selectAll("g").attr("transform", "translate(" + (pad - x-4) + "," + (pad - y-4) + ")");
      outwidth = $("#tp").parent().width() * 0.95;
      outheight = outwidth / width * height;
      svg.attr("viewBox", "0 0 " + (width + 2 * pad) + " " + (height + 2 * pad));
      svg.attr("width", outwidth);
      return svg.attr("height", outheight);
    };
    load_options = function() {
      var options_string;
      options.id = "tp";
      options.equallength = false;
      try {
        options_string = {
        		"label_fontsize": 1,
        		"label_color": "#484848",
        		"label_offset": 1.2,
        		"edge_width": 0.08,
        		"edge_color": "#fec44f",
        		"edge_scale": 1
        	};
        return options["default"] = JSON.parse(options_string);
      } catch (_error) {}
    };
    doplot = function() {
      var error;
      $("#tp").html("");
      try {
        load_options();
        plotPhylogeny(options);
        $("#plot").get(0).scrollIntoView();
        return $("#te").hide();
      } catch (_error) {
        error = _error;
        console.log(error);
        $("#te").html(String(error));
        return $("#te").show();
      }
    };
    saveplot = function() {
      var _svg, blob, svg_string;
      _svg = d3.select("svg")[0][0];
      svg_string = document.getElementById("tp").innerHTML;
      blob = new Blob([svg_string], {
        type: "image/svg+xml;base64"
      });
      return saveAs(blob, "plot.svg");
    };
    clear = function() {
      var options_string;
      $("#ts").val("");
      $("#tc").val("");
      $("#tf").val("");
      $("#tp").html("");
      reset_options();
      options_string = JSON.stringify(options["default"], null, '\t');
      return $("#op").val(options_string);
    };
    $("#plot").click(doplot);
    $("#clear").click(clear);
    $("#save").click(saveplot);
    $("#ad").click(function() {
      var options_string;
      try {
        options_string = $("#op").val();
        options["default"] = JSON.parse(options_string);
      } catch (_error) {}
      options_string = JSON.stringify(options["default"], null, '\t');
      $("#op").val(options_string);
      return $("#op").toggle();
    });
    doplot();
    return $("#example").click(function() {
      var labelcolors, labelscales, treestring;
      treestring = "(coalescence:0.045993038,deep:0.04624504,(cost:0.23139237,(duplication:0.3638669,(((gene:0.19954431,loss:0.20143054):0.1701682,lineage:0.37318188):0.034821946,(number:0.42671138,(((((rf:0.10377345,distance:0.10017748):0.23562986,equivalent:0.33383065):0.057406668,relationships:0.38811886):0.03425868,((reconciliation:0.23132662,costs:0.24479628):0.1233465,methods:0.3576585):0.0694008):0.022499183,((((((species:0.23725139,tree:0.23160231):0.036646213,space:0.270384):0.12870727,(trees:0.3850207,score:0.37763426):0.020136086):0.0419018,(defined:0.38267758,((terraces:0.2684868,size:0.26890138):0.035748273,distribution:0.30611905):0.079874806):0.05296954):0.011245258,furnas:0.4482118):0.0048618168,(implies:0.45888194,(((((assume:0.41579154,child:0.41677946):0.0234262,descendant:0.44509086):0.0077592554,((root:0.37822682,path:0.3830252):0.028375672,mapped:0.39903435):0.037732556):0.0014083103,((case:0.4295289,(lemma:0.3685342,proof:0.36388388):0.0678121):0.016068572,(leaf:0.39813396,children:0.3906566):0.049809817):0.0051155305):0.006810868,((((lca:0.28116992,map:0.27778623):0.15751457,(leaves:0.39113843,denotes:0.39502105):0.03147065):0.015321414,((subtree:0.26571432,left:0.2632676):0.17959024,fig:0.44388893):0.0022803498):0.00667195,(((node:0.2857698,nonroot:0.29209435):0.11268219,((nodes:0.27586785,internal:0.26188087):0.08705686,binary:0.35139877):0.050516304):0.032112896,cluster:0.44481996):0.014393961):0.0016197097):0.0032334558):0.0069535444):0.008581441):0.023296922):0.019111378):0.03873653):0.13201661):0.19112077);";
      labelcolors = "lca:red, proof:blue, leaf:#6d4, fig:#3182bd";
      labelscales = "rf:3, lca:2, case:1.5, left:0.7";
      $("#ts").val(treestring);
      $("#tc").val(labelcolors);
      return $("#tf").val(labelscales);
    });
  });

}).call(this);
