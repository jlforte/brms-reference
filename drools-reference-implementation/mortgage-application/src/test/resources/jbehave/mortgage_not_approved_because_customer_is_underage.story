Given there are these applications
| appId | amount | custId |
| 100   | 8000   | 1      |

And these customers
|  id  |   name   | age | creditScore |
|1     |Bob       |17   | 700         |

When I evaluate these objects in the mortgage application

Then I expect the mortgages added to be
| custId | appId | amount |
