var graph, link, node, graphRec ;
var width = 1200,
    height = 800;

var toggle = 0;
var linkedByIndex = {};
var optArray = [];
var color = d3.scale.category10();
var regEx = new RegExp("([A-Z@].+)\\w+", "g");
var mouseoverFlag= true;

var force = d3.layout.force()
	.charge(-50)
	.linkDistance(function(d) { var val = 0; if(d.value > 20) val = height/8;
						 else if(d.value >10) val = height/6; 
						 else val = height/3; return val;})
	.gravity(0.1)
	.size([width, height]);

var svg = d3.select("#chart").append("svg")
	.attr("width", width)
	.attr("height", height)
	.attr("align","center");

d3.json("forceatlas.json", function(error, json) { 
			if(error) return console.warn(error);  
			graph = json;
			graphRec = JSON.parse(JSON.stringify(graph)); 
			update(graph); });
		

var node_drag = d3.behavior.drag()
		.on("dragstart", dragstart)
		.on("drag", dragmove)
		.on("dragend", dragend);


//Set up tooltip
var tip = d3.tip()
    .attr('class', 'd3-tip')
    .offset([-10, 0])
    .html(function (d) {
    return  d.name + "";
})
svg.call(tip);


function update(graph){

	force.nodes(graph.nodes)
		.links(graph.links)
		.start();

	link = svg.selectAll(".link")
		.data(graph.links)
		.enter().append("line")
		.attr("class", "link")
		.style("stroke-width", function(d) { return Math.sqrt(d.value) < 7 ?Math.sqrt(d.value)/3:4; })
		.style("stroke", function(d) {return color(d.value); });

	node = svg.selectAll(".node")
		.data(graph.nodes)
		.enter().append("g")
		.attr("class", "node")
		.call(force.drag)
		.on('click', connectedNodes)
		.call(node_drag)
		.on('dblclick', releasenode)
		.on('mouseover', tip.show)
		.on('mouseout', tip.hide);
		//.on('mouseover', mover)
		//.on('mouseout', mout);
	
	node.append("circle")
	.attr("r", function(d) { return d.radius; })
	.style("fill",  function(d) { return color(d.radius*2); }); 
					


	node.append("text")
      	    .attr("dx", 10)
            .attr("dy", ".16em")
            .text(function(d) { return (d.name).replace(regEx, ""); })
	    .style("text-anchor", "right");

	force.charge(function(node) {
       return node.graph === 0 ? -30 : -300; });

	if(document.getElementById('lout').value == "tree"){
	  force.on("tick", function(e) { 
		var k = 6 * e.alpha;
	        // Push sources up and targets down to form a weak tree.
	        link.each(function(d) { d.source.y -= k, d.target.y += k; })
			.attr("x1", function(d) { return d.source.x; })
			.attr("y1", function(d) { return d.source.y; })
			.attr("x2", function(d) { return d.target.x; })
			.attr("y2", function(d) { return d.target.y; });

		 node.attr("cx", function(d) { return d.x; })
			.attr("cy", function(d) { return d.y; });

                 d3.selectAll("circle").attr("cx", function (d) { return d.x; })
        	        .attr("cy", function (d) { return d.y; });
	       
		 d3.selectAll("text").attr("x", function (d) { return d.x; })
		        .attr("y", function (d) { return d.y; });
		});
	
	}else{
		force.on("tick", function () { 
					link.attr("x1", function (d) { return d.source.x; })
				        .attr("y1", function (d) { return d.source.y; })
				        .attr("x2", function (d) { return d.target.x; })
				        .attr("y2", function (d) { return d.target.y; });

				        node.attr("cx", function (d) { return d.x; })
					      .attr("cy", function (d) { return d.y; });
					
					//node.attr("cx", function(d) { return Math.max(d.r, Math.min(width - d.r, d.x)); })
					  //    .attr("cy", function(d) { return Math.max(d.r, Math.min(height - d.r, d.y)); });
        
					d3.selectAll("circle").attr("cx", function (d) { return d.x; })
	        			      .attr("cy", function (d) { return d.y; });
	       
					d3.selectAll("text").attr("x", function (d) { return d.x; })
				                    .attr("y", function (d) { return d.y; });
		});
	}

	//connected nodes
	for (i = 0; i < graph.nodes.length; i++) {
	    linkedByIndex[i + "," + i] = 1;
	};

	graph.links.forEach(function (d) {
	    linkedByIndex[d.source.index + "," + d.target.index] = 1;
	});

}
	

function threshold(thresh){
	graph.links.splice(0, graph.links.length);

		for (var i= 0; i < graphRec.links.length; i++){
			if(graphRec.links[i].value > thresh) { graph.links.push(graphRec.links[i]); }
		}
	restart();
}

function restart() {
	link = link.data(graph.links);
	link.exit().remove();
	link.enter().insert("line", ".node").attr("class", "link")
		.style("stroke", function(d) {return color(d.value); })
		.style("stroke-width", function(d) { return Math.sqrt(d.value) < 7 ?Math.sqrt(d.value)/2:4; });
	node = node.data(graph.nodes);
	node.enter().insert("circle", ".cursor").attr("class", "node").attr("r", 9).call(force.drag);
	force. 	start();
}


function neighboring(a, b) {	
    return linkedByIndex[a.index + "," + b.index];
}


function mover(d){
	//mouseoverFlag=true;
	 var connected = {};
	//d = d3.select(this).node().__data__;
	
        var friends = graph.nodes.filter(function(o) { return neighboring(d, o) | neighboring(o, d); });
	friends.forEach(function(o) {
          connected[o.index] = 1;
          // second pass to get second-degree neighbours
          graph.nodes.forEach(function(p) {
            if(neighboring(o, p)) {
              connected[p.name] = 1;
            }
          });
      	});

	d3.selectAll(".link").transition().duration(500)
        .style("opacity", function(o) {
        return (connected[o.source.index] == 1 && connected[o.target.index] == 1) ? 1 : 0.01;
	});

	d3.selectAll(".node").transition().duration(500)
        .style("opacity", function(o) {
           				var op;
           				if(o == d || connected[o.name] == 1) {
             				op = 1;} else {
             				op = 0.01}return op;
        });

}
 
function mout(){
	if(mouseoverFlag==true){
	d3.selectAll(".link").transition().duration(500)
        .style("opacity", 1);
  	d3.selectAll(".node").transition().duration(500)
        .style("opacity", 1);}
	
}

function connectedNodes() {
     //mouseoverFlag=false;
     var connected = {};
     var mutual = document.getElementById('mutual').checked;	
     var second = document.getElementById('second').checked;	
     force.stop();
	
     if(second == true && mutual == false && toggle == 1 && d != d3.select(this).node().__data__){	
    	d = d3.select(this).node().__data__;
	mover(d);
	}
     else if(mutual == true && second == false && toggle == 1 && d != d3.select(this).node().__data__){
	//alert(mutual);
	d_prev =d;
	d = d3.select(this).node().__data__;
	
        var friends = graph.nodes.filter(function(o) { return neighboring(d, o) | neighboring(o, d); });
	
	friends.forEach(function(o) {
	 if(neighboring(d_prev, o) | neighboring(o, d_prev)) {
	   connected[o.index]=1;			
	}});

	d3.selectAll(".link").transition().duration(500)
        .style("opacity", function(o) {
        return (connected[o.source.index] == 1 && connected[o.target.index] == 1) ? 1 : 0.01;
	});

	d3.selectAll(".node").transition().duration(500)
        .style("opacity", function(o) {
           				var op;
           				if(o == d || connected[o.index] == 1) {
             				op = 1;} else {
             				op = 0.01}return op;
        });

        //Reduce the op
        toggle = 1;
    }		
    else if(toggle == 1 && d != d3.select(this).node().__data__){		
	d_prev =d;
	d = d3.select(this).node().__data__;
       
        node.transition().duration(1000).style("opacity", function (o) {
            return neighboring(d, o) | neighboring(o, d) ? 1 : 0.01;
        });
        link.transition().duration(1000).style("opacity", function (o) {
            return d.index==o.source.index | d.index==o.target.index ? 1 : 0.01;
        });
        //Reduce the op
        toggle = 1;
    }		
    else if (toggle == 0) {
	//alert("in ifelse");
        //Reduce the opacity of all but the neighbouring nodes
        d = d3.select(this).node().__data__;
        node.transition().duration(1000).style("opacity", function (o) {
            return neighboring(d, o) | neighboring(o, d) ? 1 : 0.01;
        });
        link.transition().duration(1000).style("opacity", function (o) {
            return d.index==o.source.index | d.index==o.target.index ? 1 : 0.01;
        });
        //Reduce the op
        toggle = 1;
    } else {
        //Put them back to opacity=1
	//alert("in else");
        node.transition()
        .duration(1000).style("opacity", 1);
        link.transition()
        .duration(1000).style("opacity", 1);
        toggle = 0;
    }
}

function dragstart(d, i) {
        force.stop() // stops the force auto positioning before you start dragging
    }
    function dragmove(d, i) {
        d.px += d3.event.dx;
        d.py += d3.event.dy;
        d.x += d3.event.dx;
        d.y += d3.event.dy;
    }
    function dragend(d, i) {
        d.fixed = true; // of course set the node to fixed so the force doesn't include the node in its auto positioning stuff
        force.resume();
    }
    function releasenode(d) {
        d.fixed = false; // of course set the node to fixed so the force doesn't include the node in its auto positioning stuff
        force.resume();
    }

function searchNode() {
//find the node
    var selectedVal = document.getElementById('search').value;
    var node = svg.selectAll(".node");
    if (selectedVal == "none") {
        node.style("stroke", "white").style("stroke-width", "1");
    } else {
    var selected = node.filter(function (d, i) {
        return d.name != selectedVal;
    });
    selected.style("opacity", "0");
    var link = svg.selectAll(".link")
        link.style("opacity", "0");
        d3.selectAll(".node, .link").transition()
        .duration(5000)
        .style("opacity", 1);
    }
}

for (var i = 0; i < graph.nodes.length - 1; i++) {
    optArray.push(graph.nodes[i].name);
}
	
$(function () {
    $("#search").autocomplete({
      source: optArray
    });
});	

