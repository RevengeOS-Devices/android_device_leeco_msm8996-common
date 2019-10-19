#!/sbin/sh
# Copyright (c) 2018, The LineageOS Project
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#     * Redistributions of source code must retain the above copyright
#       notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above copyright
#       notice, this list of conditions and the following disclaimer in the
#       documentation and/or other materials provided with the distribution.
#     * Neither the name of the LineageOS Project nor
#       the names of its contributors may be used to endorse or promote
#       products derived from this software without specific prior written
#       permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# NON-INFRINGEMENT ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
# CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
# EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
# OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
# OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
# ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#

# Unlock the free 'last_parti' to mount /vendor.
# Reverting:
# sgdisk --change-name=34:last_parti /dev/block/sde

BLOCKDEV=/dev/block/sde

safeRunCommand() {
   cmnd="$@"
   $cmnd
   ERROR_CODE=$?
   if [ ${ERROR_CODE} != 0 ]; then
      printf "Error when executing command: '${command}'\n"
      exit ${ERROR_CODE}
   fi
}

# Check for /vendor existence in partition table.
VENDOR=`/tmp/sgdisk --pretend --print $BLOCKDEV | /tmp/toybox grep -c vendor`

if [ $VENDOR -eq 0 ]; then
    # Change partition typecode to '8300 Linux filesystem'
    command="/tmp/sgdisk --typecode=34:8300 $BLOCKDEV"
    safeRunCommand $command

    # Change partition name to 'vendor'
    command="/tmp/sgdisk --change-name=34:vendor $BLOCKDEV"
    safeRunCommand $command
fi

exit 0
