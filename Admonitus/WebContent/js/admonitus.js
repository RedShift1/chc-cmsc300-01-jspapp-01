Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});


var admonitus = {};
admonitus.transFreqNoToName = function(num)
{
	switch (num)
	{
	case 0:
		return "One-time";
	case 1:
		return "Daily";
	case 2:
		return "Weekly";
	case 3:
		return "Monthly";
	default:
		return "Invalid";
	}
}
