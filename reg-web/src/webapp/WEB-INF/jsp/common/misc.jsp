<style>
#progress_indicator_panel {
    text-align: center;
    font-weight: bold;
    font-size: 110%;
    padding: 5px 5px 5px 5px;
    border-color: #808080;
    border-style: solid;
    border-width: 1px 1px 1px 1px;
    width: 240px !important;
    height: 60px !important;
    background: url("../images/bg_menu.gif") repeat-y scroll right center #E9E9E9;
    z-index: 100;
    white-space: nowrap !important;
    -moz-box-shadow: 10px 10px 5px #888;
    -webkit-box-shadow: 10px 10px 5px #888;
    box-shadow: 10px 10px 5px #888;    
}

#progress_indicator_panel div {
    padding: 2px 2px 2px 2px;
}
</style>
<div id="progress_indicator_panel" style="display: none;">
	<div style="cursor: auto;">Loading, please wait...</div>
	<div class="progress progress-striped active">
        <div class="progress-bar"  role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
         <span class="sr-only"></span>
        </div>
    </div>
</div>